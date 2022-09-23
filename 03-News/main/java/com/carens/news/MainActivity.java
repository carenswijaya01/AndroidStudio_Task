package com.carens.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends FragmentActivity implements HeadlinesFragment.OnHeadlineSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //kalo ketemu id frame_container, maka posisi lagi portrait
        if(findViewById(R.id.frame_container) != null){
            if(savedInstanceState != null){
                return;
            }

            HeadlinesFragment headlinesFragment = new HeadlinesFragment();
            headlinesFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.frame_container, headlinesFragment).commit();
        }
    }

    @Override
    public void onArticleSelected(int position) {
        //ngambil nilai boolean is_landscape dari resources
        boolean is_landscape = getApplicationContext().getResources().getBoolean(R.bool.is_landscape);

        //cek posisi landscape / portrait, landscape langsung update, portrait tuker (transaction) dulu
        if(is_landscape){
            ArticleFragment articleFragment = (ArticleFragment) getSupportFragmentManager().findFragmentById(R.id.article_fragment);
            articleFragment.updateArticleView(position);
        }else{
            Bundle args = new Bundle();
            args.putInt(ArticleFragment.ARG_POSITION, position);
            ArticleFragment newArticleFragment = new ArticleFragment();
            newArticleFragment.setArguments(args);

            //bongkar pasang fragment (begin -> commit)
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            //tuker frame container dengan article fragment
            fragmentTransaction.replace(R.id.frame_container, newArticleFragment);
            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();
        }
    }

    public void addNews(View v){
        Intent intent = new Intent(this, AddNews.class);
        startActivity(intent);
    }
}