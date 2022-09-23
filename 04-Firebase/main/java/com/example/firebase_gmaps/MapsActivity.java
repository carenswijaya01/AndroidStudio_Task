package com.example.firebase_gmaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.firebase_gmaps.databinding.ActivityMapsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private static final String TAG = MapsActivity.class.getSimpleName();
    private TextView txtDetails;
    private EditText inputLat, inputLong;
    private Button btnSave;
    private DatabaseReference mFirebaseDatabase;

    private String mapId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        inputLat = findViewById(R.id.getLat);
        inputLong = findViewById(R.id.getLong);
        btnSave = findViewById(R.id.btnSave);

        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("maps");
        mFirebaseInstance.getReference("app_title").setValue("Carens-672019084-Firebase");

        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");
                String appTitle = dataSnapshot.getValue(String.class);
                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });

        // Save / update
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String latitude = inputLat.getText().toString();
                String longitude = inputLong.getText().toString();

                if (TextUtils.isEmpty(mapId)) {
                    addMap(latitude, longitude);
                } else {
                    updateMap(latitude, longitude);
                }
            }
        });
    }

    private void addMap(String latitude, String longitude) {
        mapId = mFirebaseDatabase.push().getKey();
        Maps map = new Maps(latitude, longitude);
        mFirebaseDatabase.child(mapId).setValue(map);
        addMapChangeListener();
    }

    private void addMapChangeListener() {
        mFirebaseDatabase.child(mapId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Maps map = dataSnapshot.getValue(Maps.class);

                if (map == null) {
//                    Log.e(TAG, "Map data is null!");
                    return;
                }

//                Log.e(TAG, "Map data is changed!" + map.getLatitude() + ", " + map.getLongitude());

                inputLat.setText(String.valueOf(map.getLatitude()));
                inputLong.setText(String.valueOf(map.getLongitude()));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "Failed to read map", error.toException());
            }
        });
    }

    private void updateMap(String latitude, String longitude) {

        LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        MarkerOptions markerOptions =
                new MarkerOptions().position(latLng).title(getCompleteAddressString(latLng.latitude, latLng.longitude));

        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(new Random().nextInt(360)));
        mMap.addMarker(markerOptions);

        mFirebaseDatabase.child(mapId).child("latitude").setValue(latitude);
        mFirebaseDatabase.child(mapId).child("longitude").setValue(longitude);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng salatiga = new LatLng(-7.3305, 110.5084);
        MarkerOptions markerOptions =
                new MarkerOptions().position(salatiga).title(getCompleteAddressString(salatiga.latitude, salatiga.longitude));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(new Random().nextInt(360)));
        mMap.addMarker(markerOptions);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(salatiga, 15.0f));

        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("Current Address", strReturnedAddress.toString());
            } else {
                Log.w("Current Address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Current Address", "Can't get Address!");
        }
        return strAdd;
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        mMap.getUiSettings().setAllGesturesEnabled(false);

        btnSave.setText("Save Place");

        MarkerOptions markerOptions =
                new MarkerOptions().position(latLng).title(getCompleteAddressString(latLng.latitude, latLng.longitude));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(new Random().nextInt(360)));
        mMap.addMarker(markerOptions);

        inputLat.setText(String.valueOf(latLng.latitude));
        inputLong.setText(String.valueOf(latLng.longitude));
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        btnSave.setText("Update Place");

        LatLng latLng = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        mMap.getUiSettings().setAllGesturesEnabled(false);  

        inputLat.setText(String.valueOf(marker.getPosition().latitude));
        inputLong.setText(String.valueOf(marker.getPosition().longitude));
        return true;
    }
}