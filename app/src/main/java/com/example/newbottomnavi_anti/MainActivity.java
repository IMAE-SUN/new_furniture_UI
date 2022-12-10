package com.example.newbottomnavi_anti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Main mf;
    private Recommendation rf;
    private SettingsFragment sf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        BottomNavigationView bottomView = findViewById(R.id.bottomNavigationView);

        bottomView.setOnNavigationItemSelectedListener(listener);

        mf = new Main();
        rf = new Recommendation();
        sf = new SettingsFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, (androidx.fragment.app.Fragment)mf).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.navigation_mf:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mf).commit();
                    return true;
                case R.id.navigation_rf:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, rf).commit();
                    return true;
                case R.id.navigation_sf:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, sf).commit();
                    return true;
            }
            return false;
        }
    };
}