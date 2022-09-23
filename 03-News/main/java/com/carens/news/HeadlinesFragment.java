package com.carens.news;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

public class HeadlinesFragment extends ListFragment {

    OnHeadlineSelectedListener mCallback;

    //fungsi implementasi bagi class yg manggil Headlines Fragment
    public interface OnHeadlineSelectedListener{
        public void onArticleSelected(int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //nentuin layout & cek kalo beda versi android (lebih dari honeycomb)
//        int layout;
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
//            layout = R.layout.headline_view;
//        }else{
//            layout = R.layout.headline_view;
//        }

        //bikin adapter untuk menampilkan list
//        setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.headline_view, R.id.headline_title, Ipsum.headLines));
        setListAdapter(
                new SimpleAdapter(
                        getActivity(),
                        Ipsum.getArray(),
                        R.layout.headline_view,
                        new String[] {"headLines", "article"},
                        new int[] {R.id.headline_title, R.id.headline_intro}
                        )
        );
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //manggil mCallback
        mCallback.onArticleSelected(position);

        //yang terpilih akan jadi warna biru
        getListView().setItemChecked(position, true);
    }

    @Override
    public void onStart() {
        super.onStart();

        //cek mode lagi landscape
        if(getFragmentManager().findFragmentById(R.id.article_fragment) != null){
            //article yg bisa kepilih salah satu
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //cek OnHeadlineSelectedListener udah di implement apa belum
        try{
            mCallback = (OnHeadlineSelectedListener) context;
        }catch(ClassCastException ex){
            throw new ClassCastException(context.toString() + " must implement OnHeadlineSelectedListener");
        }
    }
}
