package com.example.newbottomnavi_anti;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Recommendation extends Fragment {

    int[] images = new int[] {R.drawable.ic_baseline_electric_bike, R.drawable.ic_baseline_emoji_emotions, R.drawable.ic_baseline_home, R.drawable.ic_baseline_settings};

    public Recommendation() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommendation, container, false);

        Log.e("추천", "추천들어옴");

        ImageView banner_a_1 = view.findViewById(R.id.img_recom_1);
        ImageView banner_a_2 = view.findViewById(R.id.img_recom_2);
        ImageView banner_a_3 = view.findViewById(R.id.img_recom_3);
        ImageView banner_a_4 = view.findViewById(R.id.img_recom_4);
//
//        Button refresh = view.findViewById(R.id.refresh_recom);
//
//        imageId1 = (int)(Math.random() * images.length);
//        if(rand1Recom==null){
//            Log.e("rand1", "null 임...");
//        }
//        rand1Recom.setBackgroundResource(images[imageId1]);
//        imageId2 = (int)(Math.random() * images.length);
//        rand2Recom.setBackgroundResource(images[imageId2]);
//        imageId3 = (int)(Math.random() * images.length);
//        rand3Recom.setBackgroundResource(images[imageId3]);
//        imageId4 = (int)(Math.random() * images.length);
//        rand4Recom.setBackgroundResource(images[imageId4]);


        Glide.with(this).load("https://img.danawa.com/prod_img/500000/325/105/img/5105325_1.jpg?shrink=330:330&_v=20190729141738").placeholder(R.drawable.ic_baseline_emoji_emotions).into(banner_a_1);
        Glide.with(this).load("https://img.danawa.com/prod_img/500000/325/105/img/5105325_2.jpg?shrink=500:500&_v=20190729141738").placeholder(R.drawable.ic_baseline_emoji_emotions).into(banner_a_2);
        Glide.with(this).load("https://img.danawa.com/prod_img/500000/325/105/img/5105325_3.jpg?shrink=500:500&_v=20190729141738").placeholder(R.drawable.ic_baseline_emoji_emotions).into(banner_a_3);
        Glide.with(this).load("https://img.danawa.com/prod_img/500000/325/105/img/5105325_4.jpg?shrink=500:500&_v=20190729141738").placeholder(R.drawable.ic_baseline_emoji_emotions).into(banner_a_4);

//        refresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                imageId1 = (int)(Math.random() * images.length);
//                rand1Recom.setBackgroundResource(images[imageId1]);
//                imageId2 = (int)(Math.random() * images.length);
//                rand2Recom.setBackgroundResource(images[imageId2]);
//                imageId3 = (int)(Math.random() * images.length);
//                rand3Recom.setBackgroundResource(images[imageId3]);
//                imageId4 = (int)(Math.random() * images.length);
//                rand4Recom.setBackgroundResource(images[imageId4]);
//            }
//        });




        return view;
    }
}