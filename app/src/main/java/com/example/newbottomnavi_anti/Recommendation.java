package com.example.newbottomnavi_anti;

import android.media.Image;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Recommendation extends Fragment {

    public Recommendation() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommendation, container, false);

        Log.e("추천", "추천들어옴");

//        카메라, 갤러리 버튼
//        TODO : 연결하기
        Button camera_btn = view.findViewById(R.id.camera_recom);
        Button gallery_btn = view.findViewById(R.id.gallery_recom);

//        그렇게 불러온 사진들 띄워주기 (최대 4개)
//        TODO : 클릭 시 없어져야 함
        ImageView wys_1 = view.findViewById(R.id.img_whatsyourstle_1);
        ImageView wys_2 = view.findViewById(R.id.img_whatsyourstle_2);
        ImageView wys_3 = view.findViewById(R.id.img_whatsyourstle_3);
        ImageView wys_4 = view.findViewById(R.id.img_whatsyourstle_4);
        
//        추천하기 버튼
//        TODO : 클릭 시 python 코드와 연결되도록
        Button recommed_btn = view.findViewById(R.id.recommend_btn);

//        refresh 버튼
//        TODO : 클릭 시 기존 정보 바탕으로 (연산 x) 다시 추천
        ImageButton refresh_btn = view.findViewById(R.id.refresh_imgbtn);

//        추천해주는 제품들 사진
//        a, b, c, d 가 각 제품
//        _1, _2 이런게 그 속에 속하는 부 사진들
        ImageView img_recom_a = view.findViewById(R.id.img_recom_a);
        ImageView img_recom_a_1 = view.findViewById(R.id.img_recom_a_1);
        ImageView img_recom_b = view.findViewById(R.id.img_recom_b);
        ImageView img_recom_b_1 = view.findViewById(R.id.img_recom_b_1);
        ImageView img_recom_c = view.findViewById(R.id.img_recom_c);
        ImageView img_recom_c_1 = view.findViewById(R.id.img_recom_c_1);
        ImageView img_recom_d = view.findViewById(R.id.img_recom_d);
        ImageView img_recom_d_1 = view.findViewById(R.id.img_recom_d_1);

//        사진찍기
        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        
//        갤러리에서 가져오기
        gallery_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

//        추천하기 (파이썬과 연결)
        recommed_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        
//        추천 새로고침
        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        
        Glide.with(this).load("https://img.danawa.com/prod_img/500000/325/105/img/5105325_1.jpg?shrink=330:330&_v=20190729141738").placeholder(R.drawable.ic_baseline_emoji_emotions).into(img_recom_a);
        Glide.with(this).load("https://img.danawa.com/prod_img/500000/325/105/img/5105325_2.jpg?shrink=500:500&_v=20190729141738").placeholder(R.drawable.ic_baseline_emoji_emotions).into(img_recom_a_1);
        Glide.with(this).load("https://img.danawa.com/prod_img/500000/325/105/img/5105325_3.jpg?shrink=500:500&_v=20190729141738").placeholder(R.drawable.ic_baseline_emoji_emotions).into(img_recom_b);
        Glide.with(this).load("https://img.danawa.com/prod_img/500000/325/105/img/5105325_4.jpg?shrink=500:500&_v=20190729141738").placeholder(R.drawable.ic_baseline_emoji_emotions).into(img_recom_b_1);
        Glide.with(this).load("https://img.danawa.com/prod_img/500000/325/105/img/5105325_1.jpg?shrink=330:330&_v=20190729141738").placeholder(R.drawable.ic_baseline_emoji_emotions).into(img_recom_c);
        Glide.with(this).load("https://img.danawa.com/prod_img/500000/325/105/img/5105325_2.jpg?shrink=500:500&_v=20190729141738").placeholder(R.drawable.ic_baseline_emoji_emotions).into(img_recom_c_1);
        Glide.with(this).load("https://img.danawa.com/prod_img/500000/325/105/img/5105325_3.jpg?shrink=500:500&_v=20190729141738").placeholder(R.drawable.ic_baseline_emoji_emotions).into(img_recom_d);
        Glide.with(this).load("https://img.danawa.com/prod_img/500000/325/105/img/5105325_4.jpg?shrink=500:500&_v=20190729141738").placeholder(R.drawable.ic_baseline_emoji_emotions).into(img_recom_d_1);



        return view;
    }
}