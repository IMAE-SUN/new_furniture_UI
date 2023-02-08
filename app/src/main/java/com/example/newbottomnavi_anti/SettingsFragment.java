package com.example.newbottomnavi_anti;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsFragment extends Fragment{
    public SettingsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_recommedation, container, false);


        Button save = (Button) view.findViewById(R.id.save_personal_recommendation_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        SeekBar seekBar = view.findViewById(R.id.seekbar);
        TextView text_seekbar = view.findViewById(R.id.text_seekbar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //TODO seekbar 조작 중
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //TODO seekbar 처음 터치했을 때
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("값", String.valueOf(seekBar.getProgress()));
                text_seekbar.setText(String.format("선택 값은 %d 입니다.", seekBar.getProgress()));
            }
        });

        return view;
    }
}

//얘도 잠시 지우고
//public class SettingsFragment extends PreferenceFragmentCompat {
//
//    @Override
//    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
//        setPreferencesFromResource(R.xml.root_preferences, rootKey);
//    }
//}

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