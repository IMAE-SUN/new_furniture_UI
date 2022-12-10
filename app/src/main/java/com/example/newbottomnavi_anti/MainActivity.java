package com.example.newbottomnavi_anti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.newbottomnavi_anti.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    AppBarConfiguration appBarConfiguration;
    private Main mf;
    private Recommendation rf;
    private SettingsFragment sf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        BottomNavigationView bottomView = findViewById(R.id.bottomNavigationView);

        //bottomView.setOnNavigationItemSelectedListener(listener);

        mf = new Main();
        rf = new Recommendation();
        sf = new SettingsFragment();

        //getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, (androidx.fragment.app.Fragment)mf).commit();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_mf, R.id.navigation_rf, R.id.navigation_sf)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
    }

//    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()){
//                case R.id.navigation_mf:
//                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mf).commit();
//                    return true;
//                case R.id.navigation_rf:
//                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, rf).commit();
//                    return true;
//                case R.id.navigation_sf:
//                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, sf).commit();
//                    return true;
//            }
//            return false;
//        }
//    };
}