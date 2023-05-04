package com.example.newbottomnavi_anti;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.HashMap;

public class SettingsFragment extends Fragment{
    public SettingsFragment(){

    }
    String RadioText;
    FirebaseAuth firebaseAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    private Socket socket;
    private String ip = "210.102.178.118";
    private int port = 12125;
    private DataOutputStream dos;
    private DataInputStream dis;

    String response = "";
    // 0. create new thread 1.open socket connection. 2.send string to server 3.receive data 4. close socket connection. 5. return response string
    public String sendToServer(String msg) {
//        String response = "";
        try {
            Thread thread = new Thread() {
                public void run() {
                    try {
                        socket = new Socket(ip, port);
                        dos = new DataOutputStream(socket.getOutputStream());
                        dis = new DataInputStream(socket.getInputStream());
                        dos.writeUTF(msg);
                        response = dis.readUTF();
                        dos.close();
                        dis.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_recommedation, container, false);

        firebaseAuth = firebaseAuth.getInstance();

        RadioGroup radiogroup = view.findViewById(R.id.radiogroup);
        RadioButton rb1 = view.findViewById(R.id.radiobutton1);
        RadioButton rb2 = view.findViewById(R.id.radiobutton2);
        RadioButton rb3 = view.findViewById(R.id.radiobutton3);
        RadioButton rb4 = view.findViewById(R.id.radiobutton4);


//        Radio Button 처리
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

//                String RadioText;
                switch (checkedId){
                    case R.id.radiobutton1:
                        Toast.makeText(getContext(), rb1.getText().toString(), Toast.LENGTH_SHORT).show();
                        Log.e("Radio Button1", rb1.getText().toString());
                        RadioText = rb1.getText().toString();
                        break;
                    case R.id.radiobutton2:
                        Toast.makeText(getContext(), rb2.getText().toString(), Toast.LENGTH_SHORT).show();
                        Log.e("Radio Button2", rb2.getText().toString());
                        RadioText = rb2.getText().toString();
                        break;
                    case R.id.radiobutton3:
                        Toast.makeText(getContext(), rb3.getText().toString(), Toast.LENGTH_SHORT).show();
                        Log.e("Radio Button3", rb3.getText().toString());
                        RadioText = rb3.getText().toString();
                        break;
                    case R.id.radiobutton4:
                        Toast.makeText(getContext(), rb4.getText().toString(), Toast.LENGTH_SHORT).show();
                        Log.e("Radio Button3", rb4.getText().toString());
                        RadioText = rb4.getText().toString();
                        break;
                }
            }
        });


        CheckBox cb1 = view.findViewById(R.id.cb_question_2_bed);
        CheckBox cb2 = view.findViewById(R.id.cb_question_2_chair);
        CheckBox cb3 = view.findViewById(R.id.cb_question_2_child);
        CheckBox cb4 = view.findViewById(R.id.cb_question_2_dining);
        CheckBox cb5 = view.findViewById(R.id.cb_question_2_interior);
        CheckBox cb6 = view.findViewById(R.id.cb_question_2_shelf);
        CheckBox cb7 = view.findViewById(R.id.cb_question_2_sofa);
        CheckBox cb8 = view.findViewById(R.id.cb_question_2_sofatable);
        CheckBox cb9 = view.findViewById(R.id.cb_question_2_study);


//        SeekBar 처리
        SeekBar seekBar = view.findViewById(R.id.seekbar);
        TextView text_seekbar = view.findViewById(R.id.text_seekbar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getContext(), String.valueOf(seekBar.getProgress()), Toast.LENGTH_SHORT).show();
                Log.e("SeekBar", String.valueOf(seekBar.getProgress()));
                text_seekbar.setText(String.format("선택 값은 %d 입니다.", seekBar.getProgress()));
            }
        });

//        save 버튼 눌렀을 때 (CheckBox 처리)
        Button button = view.findViewById(R.id.save_personal_recommendation_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), RadioText, Toast.LENGTH_SHORT).show();
                Log.e("Final Radio Button", RadioText);

                String selectedCbText = "";
                if(cb1.isChecked()){
                    selectedCbText += cb1.getText().toString() + " ";
                }
                if(cb2.isChecked()){
                    selectedCbText += cb2.getText().toString() + " ";
                }
                if(cb3.isChecked()){
                    selectedCbText += cb3.getText().toString() + " ";
                }
                if(cb4.isChecked()){
                    selectedCbText += cb4.getText().toString() + " ";
                }
                if(cb5.isChecked()){
                    selectedCbText += cb5.getText().toString() + " ";
                }
                if(cb6.isChecked()){
                    selectedCbText += cb6.getText().toString() + " ";
                }
                if(cb7.isChecked()){
                    selectedCbText += cb7.getText().toString() + " ";
                }
                if(cb8.isChecked()){
                    selectedCbText += cb8.getText().toString() + " ";
                }
                if(cb9.isChecked()){
                    selectedCbText += cb9.getText().toString() + " ";
                }

                Toast.makeText(getContext(), selectedCbText, Toast.LENGTH_SHORT).show();
                Log.e("Final CheckBox", selectedCbText);

                FirebaseUser user = firebaseAuth.getCurrentUser();
                String uid = user.getUid();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("Users");

//                분위기 전달
                reference.child(uid).child("Preference").child("Mood").setValue(RadioText);

//                가구 전달
                HashMap<Object, String> hashMap = new HashMap<>();
                String[] temp = selectedCbText.split(" ");
                int n = temp.length;
                for (int i=0; i<n; i++){
                    Log.e("CheckBox", temp[i]);
                    hashMap.put("furniture", temp[i]);
                    reference.child(uid).child("Preference").child("Furniture").push().setValue(hashMap);
                }

//                퍼센트 전달
                reference.child(uid).child("Preference").child("seekbar").setValue(String.valueOf(seekBar.getProgress()));

//                String result = sendToServer(uid);

                Toast.makeText(getContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}