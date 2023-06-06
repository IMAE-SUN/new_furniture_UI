package com.example.newbottomnavi_anti;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PreRate extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    int next_num = 1;
    String total_pick_string = "";
    HashMap<String, List<String>> imagesByMood = new HashMap<>();
    List<String> selectedImages = new ArrayList<>();
    Random random = new Random();

    List<String> selectedColdSoftImages = new ArrayList<>();
    List<String> selectedWarmSoftImages = new ArrayList<>();
    List<String> selectedColdHardImages = new ArrayList<>();
    List<String> selectedWarmHardImages = new ArrayList<>();

    public void load(){

        String[] fileNames = {"bed.txt", "chair.txt", "closet.txt", "desk.txt", "shelf.txt", "sofa.txt", "table.txt"};
        String[] coldSoft = {"Dreamy", "Charming", "Wholesome", "Tranqu", "Plain", "Fresh", "Emotional", "Fashionable", "Delicate", "Chic", "Agile", "Youthful", "Refreshing", "Clean", "Neat"};
        String[] warmSoft = {"Colorful", "Casual", "Bright", "Enjoyable", "Pretty", "Childlike", "Sweet", "Soft", "Intimate", "Mild", "Graceful"};
        String[] warmHard = {"Lively", "Bold", "Active", "Wild", "Extravagant", "Alluring", "Mellow", "Luxurious", "Trational", "Elaborate", "Heavy&Deep", "Calm"};
        String[] coldHard = {"Modest", "Quite", "Dapper", "Dignified", "Noble", "Stylish", "Sporty", "Sharp", "Rational", "Masculine", "Metallic"};

        for (String fileName : fileNames) {
            AssetManager assetManager = getAssets();
            InputStream inputStream = null;
            try {
                inputStream = assetManager.open(fileName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(";");
                    String mood = data[5];
                    String imageUrl = data[4];

                    //해당 mood가 존재하지 않으면 추가
                    if (!imagesByMood.containsKey(mood)) {
                        imagesByMood.put(mood, new ArrayList<>());
                    }
                    imagesByMood.get(mood).add(imageUrl);

                }
                reader.close();

                List<String> coldSoftImages = new ArrayList<>();
                List<String> warmSoftImages = new ArrayList<>();
                List<String> warmHardImages = new ArrayList<>();
                List<String> coldHardImages = new ArrayList<>();

                //이미지들을 4개의 그룹으로 나누고
                for (String mood : coldSoft) {
                    if (imagesByMood.containsKey(mood)) {
                        coldSoftImages.addAll(imagesByMood.get(mood));
                    }
                }

                for (String mood : warmSoft) {
                    if (imagesByMood.containsKey(mood)) {
                        warmSoftImages.addAll(imagesByMood.get(mood));
                    }
                }

                for (String mood : warmHard) {
                    if (imagesByMood.containsKey(mood)) {
                        warmHardImages.addAll(imagesByMood.get(mood));
                    }
                }

                for (String mood : coldHard) {
                    if (imagesByMood.containsKey(mood)) {
                        coldHardImages.addAll(imagesByMood.get(mood));
                    }
                }

                //각 그룹에서 이미지 4개씩을 중복되지 않게 뽑는다
                if (coldSoftImages.size() >= 4) {
                    for (int i = 0; i < 4; i++) {
                        String image;
                        do {
                            image = coldSoftImages.get(random.nextInt(coldSoftImages.size()));
                        } while (selectedColdSoftImages.contains(image));
                        selectedColdSoftImages.add(image);
                    }
                } else {
                    selectedColdSoftImages.addAll(coldSoftImages);
                }

                if(warmSoftImages.size() >= 4) {
                    for (int i = 0; i < 4; i++) {
                        String image;
                        do {
                            image = warmSoftImages.get(random.nextInt(warmSoftImages.size()));
                        } while (selectedWarmSoftImages.contains(image));
                        selectedWarmSoftImages.add(image);
                    }
                } else {
                    selectedWarmSoftImages.addAll(warmSoftImages);
                }

                if(warmHardImages.size() >= 4) {
                    for (int i = 0; i < 4; i++) {
                        String image;
                        do {
                            image = warmHardImages.get(random.nextInt(warmHardImages.size()));
                        } while (selectedWarmHardImages.contains(image));
                        selectedWarmHardImages.add(image);
                    }
                } else {
                    selectedWarmHardImages.addAll(warmHardImages);
                }

                if(coldHardImages.size() >= 4) {
                    for (int i = 0; i < 4; i++) {
                        String image;
                        do {
                            image = coldHardImages.get(random.nextInt(coldHardImages.size()));
                        } while (selectedColdHardImages.contains(image));
                        selectedColdHardImages.add(image);
                    }
                } else {
                    selectedColdHardImages.addAll(coldHardImages);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prerate);

        Log.e("PreRate", "시작");
        CheckBox cb1 = findViewById(R.id.cb_ikea_1);
        CheckBox cb2 = findViewById(R.id.cb_ikea_2);
        CheckBox cb3 = findViewById(R.id.cb_ikea_3);
        CheckBox cb4 = findViewById(R.id.cb_ikea_4);
        CheckBox cb21 = findViewById(R.id.cb_ikea_2_1);
        CheckBox cb22 = findViewById(R.id.cb_ikea_2_2);
        CheckBox cb23 = findViewById(R.id.cb_ikea_2_3);
        CheckBox cb24 = findViewById(R.id.cb_ikea_2_4);
        CheckBox cb31 = findViewById(R.id.cb_ikea_3_1);
        CheckBox cb32 = findViewById(R.id.cb_ikea_3_2);
        CheckBox cb33 = findViewById(R.id.cb_ikea_3_3);
        CheckBox cb34 = findViewById(R.id.cb_ikea_3_4);
        CheckBox cb41 = findViewById(R.id.cb_ikea_4_1);
        CheckBox cb42 = findViewById(R.id.cb_ikea_4_2);
        CheckBox cb43 = findViewById(R.id.cb_ikea_4_3);
        CheckBox cb44 = findViewById(R.id.cb_ikea_4_4);

        LinearLayout layout1 = findViewById(R.id.layout_1);
        LinearLayout layout2 = findViewById(R.id.layout_2);
        LinearLayout layout3 = findViewById(R.id.layout_3);
        LinearLayout layout4 = findViewById(R.id.layout_4);

        Button next = findViewById(R.id.next);

        ImageView img1 = findViewById(R.id.img_pre_1);
        ImageView img2 = findViewById(R.id.img_pre_2);
        ImageView img3 = findViewById(R.id.img_pre_3);
        ImageView img4 = findViewById(R.id.img_pre_4);
        ImageView img21 = findViewById(R.id.img_pre_2_1);
        ImageView img22 = findViewById(R.id.img_pre_2_2);
        ImageView img23 = findViewById(R.id.img_pre_2_3);
        ImageView img24 = findViewById(R.id.img_pre_2_4);
        ImageView img31 = findViewById(R.id.img_pre_3_1);
        ImageView img32 = findViewById(R.id.img_pre_3_2);
        ImageView img33 = findViewById(R.id.img_pre_3_3);
        ImageView img34 = findViewById(R.id.img_pre_3_4);
        ImageView img41 = findViewById(R.id.img_pre_4_1);
        ImageView img42 = findViewById(R.id.img_pre_4_2);
        ImageView img43 = findViewById(R.id.img_pre_4_3);
        ImageView img44 = findViewById(R.id.img_pre_4_4);

        String [][] strarray;
        List<Integer> list;

        /**
        COLDSOFT : Dreamy, Charming, Wholesome, Tranqu, Plain, Fresh, Emotional, Fashionable, Delicate, Chic, Agile, Youthful, Refreshing, Clean, Neat
        WARMSOFT : Colorful, Casual, Bright, Enjoyable, Pretty, Childlike, Sweet, Soft, Intimate, Mild, Graceful
        WARMHARD : Lively, Bold, Active, Wild, Extravagant, Alluring, Mellow, Luxurious, Trational, Elaborate, Heavy&Deep, Calm
        COLDHARD : Modest, Quite, Dapper, Dignified, Noble, Stylish, Sporty, Sharp, Rational, Masculine, Metallic
        **/


        firebaseAuth = firebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();
        Log.e("uid", uid);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users");
        load();
        Log.d("몇개", String.valueOf(selectedColdHardImages.size()));
        Log.d("몇개", String.valueOf(selectedWarmHardImages.size()));
        Log.d("몇개", String.valueOf(selectedColdSoftImages.size()));
        Log.d("몇개", String.valueOf(selectedWarmSoftImages.size()));

        //각 페이지당 분위기 4개씩 각각 사진 띄운다
        Glide.with(getApplicationContext()).load(selectedColdSoftImages.get(0)).into(img1);
        Glide.with(getApplicationContext()).load(selectedWarmSoftImages.get(0)).into(img2);
        Glide.with(getApplicationContext()).load(selectedWarmHardImages.get(0)).into(img3);
        Glide.with(getApplicationContext()).load(selectedColdHardImages.get(0)).into(img4);
        Glide.with(getApplicationContext()).load(selectedColdSoftImages.get(1)).into(img21);
        Glide.with(getApplicationContext()).load(selectedWarmSoftImages.get(1)).into(img22);
        Glide.with(getApplicationContext()).load(selectedWarmHardImages.get(1)).into(img23);
        Glide.with(getApplicationContext()).load(selectedColdHardImages.get(1)).into(img24);
        Glide.with(getApplicationContext()).load(selectedColdSoftImages.get(2)).into(img31);
        Glide.with(getApplicationContext()).load(selectedWarmSoftImages.get(2)).into(img32);
        Glide.with(getApplicationContext()).load(selectedWarmHardImages.get(2)).into(img33);
        Glide.with(getApplicationContext()).load(selectedColdHardImages.get(2)).into(img34);
        Glide.with(getApplicationContext()).load(selectedColdSoftImages.get(3)).into(img41);
        Glide.with(getApplicationContext()).load(selectedWarmSoftImages.get(3)).into(img42);
        Glide.with(getApplicationContext()).load(selectedWarmHardImages.get(3)).into(img43);
        Glide.with(getApplicationContext()).load(selectedColdHardImages.get(3)).into(img44);

        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(next_num==1){
                    if(cb1.isChecked()){
                        cb2.setChecked(false);
                        cb3.setChecked(false);
                        cb4.setChecked(false);
                        total_pick_string = "1 ";
                    }
                    if(cb2.isChecked()){
                        cb1.setChecked(false);
                        cb3.setChecked(false);
                        cb4.setChecked(false);
                        total_pick_string = "2 ";
                    }
                    if(cb3.isChecked()){
                        cb1.setChecked(false);
                        cb2.setChecked(false);
                        cb4.setChecked(false);
                        total_pick_string = "3 ";
                    }
                    if(cb4.isChecked()){
                        cb1.setChecked(false);
                        cb2.setChecked(false);
                        cb3.setChecked(false);
                        total_pick_string = "4 ";
                    }
//                    if(cb1.isChecked()){
//                        total_pick_string += "1 ";
//                        //coldsoft
//                    }
//                    if(cb2.isChecked()){
//                        total_pick_string += "2 ";
//                        //warmsoft
//                    }
//                    if(cb3.isChecked()){
//                        total_pick_string += "3 ";
//                        //warmhard
//                    }
//                    if(cb4.isChecked()){
//                        total_pick_string += "4 ";
//                        //coldhard
//                    }
                    layout1.setVisibility(View.GONE);
                    layout2.setVisibility(View.VISIBLE);
                    next.setText("next (2/4)");
                    next_num++;
                }
                else if(next_num==2){
                    String temp_string = "";
                    if(cb21.isChecked()){
                        cb22.setChecked(false);
                        cb23.setChecked(false);
                        cb24.setChecked(false);
                        temp_string = "1 ";
                    }
                    if(cb22.isChecked()){
                        cb21.setChecked(false);
                        cb23.setChecked(false);
                        cb24.setChecked(false);
                        temp_string = "2 ";
                    }
                    if(cb23.isChecked()){
                        cb21.setChecked(false);
                        cb22.setChecked(false);
                        cb24.setChecked(false);
                        temp_string = "3 ";
                    }
                    if(cb24.isChecked()){
                        cb21.setChecked(false);
                        cb22.setChecked(false);
                        cb23.setChecked(false);
                        temp_string = "4 ";
                    }
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
                    //TODO : 해당 정보 서버에 넘기고 다음 값 받기
                    // 다음 값 받아서 layout2 에 띄우기\
                    layout2.setVisibility(View.GONE);
                    layout3.setVisibility(View.VISIBLE);
                    next.setText("next (3/4)");
                    next_num++;
                    total_pick_string += temp_string;
                }
                else if(next_num==3){
                    String temp_string = "";
                    if(cb31.isChecked()){
                        cb32.setChecked(false);
                        cb33.setChecked(false);
                        cb34.setChecked(false);
                        temp_string = "1 ";
                    }
                    if(cb32.isChecked()){
                        cb31.setChecked(false);
                        cb33.setChecked(false);
                        cb34.setChecked(false);
                        temp_string = "2 ";
                    }
                    if(cb33.isChecked()){
                        cb31.setChecked(false);
                        cb32.setChecked(false);
                        cb34.setChecked(false);
                        temp_string = "3 ";
                    }
                    if(cb34.isChecked()){
                        cb31.setChecked(false);
                        cb32.setChecked(false);
                        cb33.setChecked(false);
                        temp_string = "4 ";
                    }
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
                    //TODO : 해당 정보 서버에 넘기고 다음 값 받기
                    // 다음 값 받아서 layout2 에 띄우기\
                    layout3.setVisibility(View.GONE);
                    layout4.setVisibility(View.VISIBLE);
                    next.setText("submit");
                    next_num++;
                    total_pick_string += temp_string;
                }
                else{
                    String temp_string = "";
                    if(cb41.isChecked()){
                        cb42.setChecked(false);
                        cb43.setChecked(false);
                        cb44.setChecked(false);
                        temp_string = "1 ";
                    }
                    if(cb42.isChecked()){
                        cb41.setChecked(false);
                        cb43.setChecked(false);
                        cb44.setChecked(false);
                        temp_string = "2 ";
                    }
                    if(cb43.isChecked()){
                        cb41.setChecked(false);
                        cb42.setChecked(false);
                        cb44.setChecked(false);
                        temp_string = "3 ";
                    }
                    if(cb44.isChecked()){
                        cb41.setChecked(false);
                        cb42.setChecked(false);
                        cb43.setChecked(false);
                        temp_string = "4 ";
                    }

                    total_pick_string += temp_string;
                    HashMap<Object, String> hashMap = new HashMap<>();
                    String[] temp = total_pick_string.split(" ");
                    int n = temp.length;
                    for (int i=0; i<n; i++){
                        Log.e("CheckBox", temp[i]);
                        hashMap.put("like", temp[i]);
                        reference.child(uid).child("PreRate").push().setValue(hashMap);
                    }

                    Intent intent = new Intent(PreRate.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    Log.e("PreRate", "완료");
                }

            }
        });

    }
}
