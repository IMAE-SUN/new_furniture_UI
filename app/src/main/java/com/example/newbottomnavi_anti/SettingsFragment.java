package com.example.newbottomnavi_anti;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}

// 이걸 하면 설정 페이지가 뜨긴 뜨는데 설정페이지 -> 다른 페이지로 넘어가면 계속 null 문제가 생깁니다..
//public class SettingsFragment extends PreferenceFragmentCompat {
//
//    SharedPreferences pref;
//    public void onCreate(Bundle savedInstancesState){
//        super.onCreate(savedInstancesState);
//
//        addPreferencesFromResource(R.xml.root_preferences);
//
//        Log.e("세팅", "세팅들어옴");
//
//        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
//
//        boolean isSound= pref.getBoolean("sound", false);
//        Toast.makeText(getActivity(), "소리알림" + isSound, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onCreatePreferences(@Nullable Bundle savedInstanceState, String rootKey) {
//        Log.e("세팅", "세팅 이상한 create");
//    }
//}