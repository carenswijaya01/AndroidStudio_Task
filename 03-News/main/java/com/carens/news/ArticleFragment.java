package com.carens.news;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ArticleFragment extends Fragment {
    final static String ARG_POSITION = "position";
    //cek artikel ke berapa yg dipilih
    int mCurrentPosition = 0;

    private TextView txtArticle, txtTitle;
    private ImageView imgView;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //proteksi supaya fragement positionnya ga ilang
        if(savedInstanceState != null){
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        //bikin view sendiri based on article_view.xml
        view = inflater.inflate(R.layout.article_view, container, false);
        txtArticle = view.findViewById(R.id.article);
        txtTitle = view.findViewById(R.id.title);
        imgView = view.findViewById(R.id.img);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();

        //manggil article
        if(args != null){
            updateArticleView(args.getInt(ARG_POSITION));
        }else{
            updateArticleView(mCurrentPosition);
        }
    }

    public void updateArticleView(int position){
        //nampilin article
        txtArticle.setText(Ipsum.arrayList.get(position).get("article"));
        txtTitle.setText(Ipsum.arrayList.get(position).get("headLines"));

        Context context = imgView.getContext();
        String imgFileName = "img_" + position;
        int drawableId = this.getResources().getIdentifier(imgFileName, "mipmap", context.getPackageName());
        imgView.setImageResource(drawableId);
        mCurrentPosition = position;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        //nge set onPause yg di save dengan position sekarang
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }
}
