package com.carens.activity_intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText eTUsername, eTPassword;
    private String username, fullname, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eTUsername = findViewById(R.id.username_login);
        eTPassword = findViewById(R.id.password_login);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        fullname = intent.getStringExtra("fullname");
        password = intent.getStringExtra("password");
    }

    public void logins(View v) {
        String username_login = eTUsername.getText().toString();
        String password_login = eTPassword.getText().toString();

        if(username_login.isEmpty() || password_login.isEmpty()){
            Toast toast = new Toast(this);
            toast.makeText(this, "Please, fill the form correctly!", Toast.LENGTH_SHORT).show();
        }else if(username_login.equals(username)){
            if(password_login.equals(password)){
                Intent intent = new Intent(this, Home.class);
                intent.putExtra("fullname", fullname);
                startActivity(intent);
            }else{
                Toast toast = new Toast(this);
                toast.makeText(this, "Wrong Password!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast toast = new Toast(this);
            toast.makeText(this, "Account not found!", Toast.LENGTH_SHORT).show();
        }
    }
}