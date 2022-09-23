package com.carens.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AddNews extends AppCompatActivity {

    private TextView txtNewTitle, txtNewArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        txtNewTitle = findViewById(R.id.newTitle);
        txtNewArticle = findViewById(R.id.newArticle);
    }

    public void saveNews(View v){
        String newTitle = txtNewTitle.getText().toString();
        String newArticle = txtNewArticle.getText().toString();

        Ipsum.addArray(newTitle, newArticle);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}