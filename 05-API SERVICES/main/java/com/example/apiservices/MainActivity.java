package com.example.apiservices;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView txtNIM, txtNama, txtUmur, txtAlamat, txtGaji;
    private EditText fldIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNIM = findViewById(R.id.txtNIM);
        txtNama = findViewById(R.id.txtNama);
        txtUmur = findViewById(R.id.txtUmur);
        txtAlamat = findViewById(R.id.txtAlamat);
        txtGaji = findViewById(R.id.txtGaji);

        fldIndex = findViewById(R.id.fldIndex);
    }

    public void hitungRataGaji(View v){
        APIList apis = RetrofitClient.getRetrofitClient().create(APIList.class);
        Call<ArrayList<Pegawai>> call = apis.getAllPegawai();
        call.enqueue(new Callback<ArrayList<Pegawai>>() {
            @Override
            public void onResponse(Call<ArrayList<Pegawai>> call, Response<ArrayList<Pegawai>> response) {
                if(response.isSuccessful()){
                    ArrayList<Pegawai> data = response.body();

                    double rataGaji = 0;
                    double sum = 0;
                    for(int i = 0; i < data.size(); i++){
                        sum += Double.parseDouble(data.get(i).getSalary().replace(",", ""));
                    }

                    rataGaji = (double) sum/data.size();

                    NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMANY);
                    DecimalFormat df = (DecimalFormat)nf;
                    df.applyPattern("###,###.##");

                    txtGaji.setText("Rp " + df.format(rataGaji));
                }else{
                    Toast.makeText(getApplicationContext(),"Respon server gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Pegawai>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Gagal kirim / dapatkan data dari server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cariMahasiswa(View v){
        APIList apis = RetrofitClient.getRetrofitClient().create(APIList.class);
        Call<ArrayList<Mahasiswa>> call = apis.getAllMahasiswa();
        call.enqueue(new Callback<ArrayList<Mahasiswa>>() {
            @Override
            public void onResponse(Call<ArrayList<Mahasiswa>> call, Response<ArrayList<Mahasiswa>> response) {
                if(response.isSuccessful()){
                    ArrayList<Mahasiswa> data = response.body();

                    if(Integer.parseInt(fldIndex.getText().toString()) > (data.size()-1)){
                        txtNIM.setText("-");
                        txtNama.setText("-");
                        txtUmur.setText("-");
                        txtAlamat.setText("-");
                        Toast.makeText(getApplicationContext(),"Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }else {
                        String name = data.get(Integer.parseInt(fldIndex.getText().toString())).getName();
                        int nim = data.get(Integer.parseInt(fldIndex.getText().toString())).getNim();
                        int age = data.get(Integer.parseInt(fldIndex.getText().toString())).getAge();
                        String address = data.get(Integer.parseInt(fldIndex.getText().toString())).getAddress();

                        txtNIM.setText(String.valueOf(nim));
                        txtNama.setText(name);
                        txtUmur.setText(String.valueOf(age));
                        txtAlamat.setText(address);
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Respon server gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Mahasiswa>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Gagal kirim / dapatkan data dari server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}