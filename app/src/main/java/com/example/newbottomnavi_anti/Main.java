package com.example.newbottomnavi_anti;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Main extends Fragment {

    int[] images = new int[] {R.drawable.ic_baseline_electric_bike, R.drawable.ic_baseline_emoji_emotions, R.drawable.ic_baseline_home, R.drawable.ic_baseline_settings};

    public Main() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Log.e("메인", "메인 들어옴");

        ImageView banner_a_1 = view.findViewById(R.id.banner_a_1);
        ImageView banner_a_2 = view.findViewById(R.id.banner_a_2);
        ImageView banner_a_3 = view.findViewById(R.id.banner_a_3);
        ImageView banner_a_4 = view.findViewById(R.id.banner_a_4);

        // Inflate the layout for this fragment
        Glide.with(this).load("https://img.danawa.com/prod_img/500000/325/105/img/5105325_1.jpg?shrink=330:330&_v=20190729141738").placeholder(R.drawable.ic_baseline_emoji_emotions).into(banner_a_1);
        Glide.with(this).load("https://img.danawa.com/prod_img/500000/325/105/img/5105325_2.jpg?shrink=500:500&_v=20190729141738").placeholder(R.drawable.ic_baseline_emoji_emotions).into(banner_a_2);
        Glide.with(this).load("https://img.danawa.com/prod_img/500000/325/105/img/5105325_3.jpg?shrink=500:500&_v=20190729141738").placeholder(R.drawable.ic_baseline_emoji_emotions).into(banner_a_3);
        Glide.with(this).load("https://img.danawa.com/prod_img/500000/325/105/img/5105325_4.jpg?shrink=500:500&_v=20190729141738").placeholder(R.drawable.ic_baseline_emoji_emotions).into(banner_a_4);

        return view;
    }

//    public void onClick(View v){
//
//        ImageView frameimg = v.findViewById(R.id.frameimg);
//
//        switch (v.getId()) {
//            case R.id.card_rand1:
//                Glide.with(getActivity()).load("https://img.danawa.com/prod_img/500000/412/126/img/11126412_1.jpg?shrink=130:130&_v=20220527163310").into(frameimg);
//                break;
//        }
//    }
}