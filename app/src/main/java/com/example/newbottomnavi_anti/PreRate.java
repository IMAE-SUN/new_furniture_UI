//package com.example.newbottomnavi_anti;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.LinearLayout;
//
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.bumptech.glide.Glide;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//public class PreRate extends AppCompatActivity {
//
//    FirebaseAuth firebaseAuth;
//    int next_num = 1;
//    String total_pick_string = "";
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_prerate);
//
//        Log.e("PreRate", "시작");
//        CheckBox cb1 = findViewById(R.id.cb_ikea_1);
//        CheckBox cb2 = findViewById(R.id.cb_ikea_2);
//        CheckBox cb3 = findViewById(R.id.cb_ikea_3);
//        CheckBox cb4 = findViewById(R.id.cb_ikea_4);
//        CheckBox cb21 = findViewById(R.id.cb_ikea_2_1);
//        CheckBox cb22 = findViewById(R.id.cb_ikea_2_2);
//        CheckBox cb23 = findViewById(R.id.cb_ikea_2_3);
//        CheckBox cb24 = findViewById(R.id.cb_ikea_2_4);
//        CheckBox cb31 = findViewById(R.id.cb_ikea_3_1);
//        CheckBox cb32 = findViewById(R.id.cb_ikea_3_2);
//        CheckBox cb33 = findViewById(R.id.cb_ikea_3_3);
//        CheckBox cb34 = findViewById(R.id.cb_ikea_3_4);
//
//        LinearLayout layout1 = findViewById(R.id.layout_1);
//        LinearLayout layout2 = findViewById(R.id.layout_2);
//        LinearLayout layout3 = findViewById(R.id.layout_3);
//
//        Button next = findViewById(R.id.next);
//        String [][] strarray;
//        List<Integer> list;
//
//        //0~39까지의 중복 없는 난수 11개 생성
//        Set<Integer> set = new HashSet<>();
//
//        while (set.size() < 11) {
//            Double d = Math.random() * 112;
//            set.add(d.intValue());
//        }
//
//        list = new ArrayList<>(set);
//
//        //filter
//        Glide.with(getApplicationContext()).load(strarray[list.get(random_pick_lst.get(0))][5]).into(binding.filterImage1);
//        Glide.with(getApplicationContext()).load(strarray[list.get(random_pick_lst.get(1))][5]).into(binding.filterImage2);
//        Glide.with(getApplicationContext()).load(strarray[list.get(random_pick_lst.get(2))][5]).into(binding.filterImage3);
//        Glide.with(getApplicationContext()).load(strarray[list.get(random_pick_lst.get(3))][5]).into(binding.filterImage4);
//
//        firebaseAuth = firebaseAuth.getInstance();
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//        String uid = user.getUid();
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference reference = database.getReference("Users");
//
//        next.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if(next_num==1){
//                    if(cb1.isChecked()){
//                        total_pick_string += "1 ";
//                    }
//                    if(cb2.isChecked()){
//                        total_pick_string += "2 ";
//                    }
//                    if(cb3.isChecked()){
//                        total_pick_string += "3 ";
//                    }
//                    if(cb4.isChecked()){
//                        total_pick_string += "4 ";
//                    }
//                    //TODO : 해당 정보 서버에 넘기고 다음 값 받기
//                    // 다음 값 받아서 layout2 에 띄우기
//                    layout1.setVisibility(View.GONE);
//                    layout2.setVisibility(View.VISIBLE);
//                    next.setText("next (2/3)");
//                    next_num++;
//                }
//                else if(next_num==2){
//                    if(cb21.isChecked()){
//                        total_pick_string += "21 ";
//                    }
//                    if(cb22.isChecked()){
//                        total_pick_string += "22 ";
//                    }
//                    if(cb23.isChecked()){
//                        total_pick_string += "23 ";
//                    }
//                    if(cb24.isChecked()){
//                        total_pick_string += "24 ";
//                    }
//                    //TODO : 해당 정보 서버에 넘기고 다음 값 받기
//                    // 다음 값 받아서 layout2 에 띄우기
//                    layout2.setVisibility(View.GONE);
//                    layout3.setVisibility(View.VISIBLE);
//                    next.setText("submit");
//                    next_num++;
//                }
//                else{
//                    if(cb31.isChecked()){
//                        total_pick_string += "31 ";
//                    }
//                    if(cb32.isChecked()){
//                        total_pick_string += "32 ";
//                    }
//                    if(cb33.isChecked()){
//                        total_pick_string += "33 ";
//                    }
//                    if(cb34.isChecked()){
//                        total_pick_string += "34 ";
//                    }
//
//                    HashMap<Object, String> hashMap = new HashMap<>();
//                    String[] temp = total_pick_string.split(" ");
//                    int n = temp.length;
//                    for (int i=0; i<n; i++){
//                        Log.e("CheckBox", temp[i]);
//                        hashMap.put("like", temp[i]);
//                        reference.child(uid).child("PreRate").push().setValue(hashMap);
//                    }
//
//                    Intent intent = new Intent(PreRate.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                    Log.e("PreRate", "완료");
//                }
//
//            }
//        });
//
//    }
//}
