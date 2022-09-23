package com.carens.activity_intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText eTUsername, eTFullname, eTPassword, eTConPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //dapetin semua string inputan user
        eTUsername = findViewById(R.id.username);
        eTFullname = findViewById(R.id.fullname);
        eTPassword = findViewById(R.id.password);
        eTConPass = findViewById(R.id.con_pass);
    }

    public void registrasi(View v){
        String username = eTUsername.getText().toString();
        String fullname = eTFullname.getText().toString();
        String password = eTPassword.getText().toString();
        String con_pass = eTConPass.getText().toString();
        if(username.isEmpty() || password.isEmpty() || fullname.isEmpty() || con_pass.isEmpty()) {
            Toast toast = new Toast(this);
            toast.makeText(this, "Please, fill the form correctly!", Toast.LENGTH_SHORT).show();
        }else if(con_pass.equals(password)){
            Intent intent = new Intent(this, Login.class);
            intent.putExtra("username", username);
            intent.putExtra("fullname", fullname);
            intent.putExtra("password", password);
            startActivity(intent);
        }
        else{
            Toast toast = new Toast(this);
            toast.makeText(this, "Password not match!", Toast.LENGTH_SHORT).show();
        }
    }
}