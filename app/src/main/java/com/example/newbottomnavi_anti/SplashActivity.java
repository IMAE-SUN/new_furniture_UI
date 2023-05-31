package com.example.newbottomnavi_anti;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    Animation anim_splash_FadeIn;
    Animation anim_fur;
    ImageView house;
    TextView furniture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        furniture = findViewById(R.id.tv_furniture_big);
        house = findViewById(R.id.img_house);

        anim_splash_FadeIn = AnimationUtils.loadAnimation(this, R.anim.anim_splash_fadein);
        anim_fur = AnimationUtils.loadAnimation(this, R.anim.anim_splash_fur);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                furniture.startAnimation(anim_splash_FadeIn);
                house.startAnimation(anim_fur);
                Intent intent = new Intent(SplashActivity.this, signin.class);
                startActivity(intent);
                finish();
            }
        }, 2000);

    }
}
