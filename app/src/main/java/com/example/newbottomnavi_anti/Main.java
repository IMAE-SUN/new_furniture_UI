package com.example.newbottomnavi_anti;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.newbottomnavi_anti.databinding.FragmentMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main extends Fragment {

    Main mainFragment;
    private FragmentMainBinding binding;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();
    FirebaseAuth firebaseAuth;
    String [][] strarray;
    List<Integer> list;
    String preRate, like;
    int from = 0, size = 5185;
    int n1 = 0, n2 = 0;

    int coldSoftCount = 0;
    int warmHardCount = 0;
    int coldHardCount = 0;
    int warmSoftCount = 0;

    public Main() {
        // Required empty public constructor
    }

    public void load(){

        /**
         * <TODO>
         *     1.coldsoft, warmhard... 비율에 따라 Main화면에 처음 추천해주기
         *     2.그 이후 메인창에서 선택된 가구들은 -> 전체 좋아요 개수를 집계하고,좋아요 개수 n개 이상 됐다 하면
         *     분위기 별 개수 / 전체 좋아요 개수로 비율을 상정, 다시 동적을 새로고침 될 수 있도록 하기.
         *     3.여기서 예외처리로, 특정 분위기에 포함된 가구는 몇개 없으면 -> 주변 분위기것까지 포함해서 띄우기
         * </TODO> :
         */

        //침대 40개, 책상 36개, 소파36개
        strarray = new String[5185][6];

        String[] assets_arr = new String[]{"bed.txt", "chair.txt", "closet.txt", "curtain.txt", "desk.txt", "lamp.txt", "shelf.txt", "sofa.txt", "table.txt"};
//        String[] category_arr = new String[] {"bed", "chair", "closet", "curtain", "desk", "lamp", "shelf", "sofa", "table"};
//        String[][] bed, chair, closet, curtain, desk, lamp, shelf, sofa, table;
        AssetManager assetManager = getActivity().getAssets();
        InputStream inputStream = null;
        int total = 0;
        try {
//            for (int i=0; i<assets_arr.length; i++) {
//                inputStream = assetManager.open(assets_arr[i]);
//                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//                String line;
//                int j = 0;
//                j=0;
//                while ((line = reader.readLine()) != null) {
//                    Log.e("line", line);
//                    strarray[j][i] = line;
//
//                    j++;
//                }
//                Log.e(assets_arr[i], j + "개");
//                total+= j;
//                reader.close();
//            }
            int j = 0;
            for(int i=0; i<9; i++){
                inputStream = assetManager.open(assets_arr[i]);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    if(i==3 || i==5){
                        String[] temp = new String[6];
                        temp = line.split(";");
                        strarray[j][5] = temp[4];
                        strarray[j][4] = temp[3];
                        strarray[j][3] = "크기(가로x세로x높이):0";
                        strarray[j][2] = temp[2];
                        strarray[j][1] = temp[1];
                        strarray[j][0] = temp[0];
                    }else{
                        strarray[j] = line.split(";");
                    }
                    j++;
                }
                reader.close();
            }
//            inputStream = assetManager.open("furniture.txt");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//            String line;
//
//            int i = 0;
//            while ((line = reader.readLine()) != null) {
//                strarray[i] = line.split(";");
//                i++;
//            }
//            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e ) {
            e.printStackTrace();
        }

        //0~39까지의 중복 없는 난수 11개 생성
        Set<Integer> set = new HashSet<>();

        while (set.size() < 15) {
            Double d = Math.random() * 5185;
            set.add(d.intValue());
        }

        list = new ArrayList<>(set);

        //filter
        Glide.with(getActivity()).load(strarray[list.get(0)][4]).into(binding.filterImage1);
        Glide.with(getActivity()).load(strarray[list.get(1)][4]).into(binding.filterImage2);
        Glide.with(getActivity()).load(strarray[list.get(2)][4]).into(binding.filterImage3);
        Glide.with(getActivity()).load(strarray[list.get(3)][4]).into(binding.filterImage4);
        Glide.with(getActivity()).load(strarray[list.get(4)][4]).into(binding.filterImage5);
        Glide.with(getActivity()).load(strarray[list.get(5)][4]).into(binding.filterImage6);

        //like
        Glide.with(getActivity()).load(strarray[list.get(12)][4]).into(binding.likeImage1);
        Glide.with(getActivity()).load(strarray[list.get(13)][4]).into(binding.likeImage2);
        Glide.with(getActivity()).load(strarray[list.get(14)][4]).into(binding.likeImage3);


        binding.titleRecently1.setText(strarray[list.get(0)][0]);
        binding.titleRecently2.setText(strarray[list.get(1)][0]);
        binding.titleRecently3.setText(strarray[list.get(2)][0]);
        binding.titleRecently4.setText(strarray[list.get(3)][0]);
        binding.titleRecently5.setText(strarray[list.get(4)][0]);
        binding.titleRecently6.setText(strarray[list.get(5)][0]);

        binding.priceRecently1.setText(strarray[list.get(0)][1] + " ₩");
        binding.priceRecently2.setText(strarray[list.get(1)][1] + " ₩");
        binding.priceRecently3.setText(strarray[list.get(2)][1] + " ₩");
        binding.priceRecently4.setText(strarray[list.get(3)][1] + " ₩");
        binding.priceRecently5.setText(strarray[list.get(4)][1] + " ₩");
        binding.priceRecently6.setText(strarray[list.get(5)][1] + " ₩");

        binding.likeTitle1.setText(strarray[list.get(12)][0]);
        binding.likeTitle2.setText(strarray[list.get(13)][0]);
        binding.likeTitle3.setText(strarray[list.get(14)][0]);

        binding.likePrice1.setText(strarray[list.get(12)][1] +" ₩");
        binding.likePrice2.setText(strarray[list.get(13)][1] +" ₩");
        binding.likePrice3.setText(strarray[list.get(14)][1] +" ₩");



        firebaseAuth = firebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();
        Log.e("uid", uid);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //firebase에서 prerate 선택 정보 가져오기
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("PreRate");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String like = snapshot.child("like").getValue(String.class);
                    Log.e("like", String.valueOf(like));
                    if (like.equals("1")) {
                        coldSoftCount++;
                    } else if (like.equals("2")) {
                        warmHardCount++;
                    } else if (like.equals("3")) {
                        coldHardCount++;
                    } else if (like.equals("4")) {
                        warmSoftCount++;
                    }
                }
                Log.e("value", "coldsoft :" + coldSoftCount);
                Log.e("value", "warmhard" + warmHardCount);
                Log.e("value", "coldhard" + coldHardCount);
                Log.e("value", "warmsoft" + warmSoftCount);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 에러 처리
            }
        });

    }

// 가구 필터 눌렀을 때 로드되기
    public void loadWithFilter(int n1, int n2){
        Log.e("loadWithFilter", "n1 : " + n1 + " n2 : " + n2);
        // 0부터 all ~
        if(n1==0) {size = 5185; from = 0;}
        else if(n1==1) {size = 714; from = 0;}
        else if(n1==2) {size = 666; from = 714;}
        else if(n1==3) {size = 680; from = 1380;}
        else if(n1==4) {size = 269; from = 2060;}
        else if(n1==5) {size = 667; from = 2329;}
        else if(n1==6) {size = 176; from = 2996;}
        else if(n1==7) {size = 688; from = 3172;}
        else if(n1==8) {size = 720; from = 3860;}
        else if(n1==9) {size = 605; from = 4580;}
        else {size = 5185; from = 0;}

        //0~39까지의 중복 없는 난수 11개 생성
        Set<Integer> set = new HashSet<>();

        String[] coldSoft = {"Dreamy", "Charming", "Wholesome", "Tranqu", "Plain", "Fresh", "Emotional", "Fashionable", "Delicate", "Chic", "Agile", "Youthful", "Refreshing", "Clean", "Neat"};
        String[] warmSoft = {"Colorful", "Casual", "Bright", "Enjoyable", "Pretty", "Childlike", "Sweet", "Soft", "Intimate", "Mild", "Graceful"};
        String[] warmHard = {"Lively", "Bold", "Active", "Wild", "Extravagant", "Alluring", "Mellow", "Luxurious", "Trational", "Elaborate", "Heavy&Deep", "Calm"};
        String[] coldHard = {"Modest", "Quite", "Dapper", "Dignified", "Noble", "Stylish", "Sporty", "Sharp", "Rational", "Masculine", "Metallic"};

        while (set.size() < 15) {
            Double d = Math.random() * size;
            Log.d("random", String.valueOf(d.intValue()));
            Log.d("from", String.valueOf(from));
            Log.d("size", String.valueOf(size));
            Log.d("strarray", strarray[from + d.intValue()][5]);
            if(n2==1){
                if(Arrays.asList(coldSoft).contains(strarray[from + d.intValue()][5])){
                    set.add(d.intValue());
                    Log.e("set", String.valueOf(set.size()) + " : " + strarray[from + d.intValue()][5]);
                }
            }
            else if(n2==2){
                if(Arrays.asList(coldHard).contains(strarray[from + d.intValue()][5])){
                    set.add(d.intValue());
                }
            }
            else if(n2==3){
                if(Arrays.asList(warmSoft).contains(strarray[from + d.intValue()][5])){
                    set.add(d.intValue());
                }
            }
            else if(n2==4){
                if(Arrays.asList(warmHard).contains(strarray[from + d.intValue()][5])){
                    set.add(d.intValue());
                }
            }
            else if(n2==0){
                set.add(d.intValue());
            }
            else{
                continue;
            }
        }

        list = new ArrayList<>(set);
//        Log.e("list", String.valueOf(list));

        Glide.with(getActivity()).load(strarray[from + list.get(0)][4]).into(binding.filterImage1);
        Glide.with(getActivity()).load(strarray[from + list.get(1)][4]).into(binding.filterImage2);
        Glide.with(getActivity()).load(strarray[from + list.get(2)][4]).into(binding.filterImage3);
        Glide.with(getActivity()).load(strarray[from + list.get(3)][4]).into(binding.filterImage4);
        Glide.with(getActivity()).load(strarray[from + list.get(4)][4]).into(binding.filterImage5);
        Glide.with(getActivity()).load(strarray[from + list.get(5)][4]).into(binding.filterImage6);

        binding.titleRecently1.setText(strarray[from + list.get(0)][0]);
        binding.titleRecently2.setText(strarray[from + list.get(1)][0]);
        binding.titleRecently3.setText(strarray[from + list.get(2)][0]);
        binding.titleRecently4.setText(strarray[from + list.get(3)][0]);
        binding.titleRecently5.setText(strarray[from + list.get(4)][0]);
        binding.titleRecently6.setText(strarray[from + list.get(5)][0]);

        binding.priceRecently1.setText(strarray[from + list.get(0)][1] + " ₩");
        binding.priceRecently2.setText(strarray[from + list.get(1)][1] + " ₩");
        binding.priceRecently3.setText(strarray[from + list.get(2)][1] + " ₩");
        binding.priceRecently4.setText(strarray[from + list.get(3)][1] + " ₩");
        binding.priceRecently5.setText(strarray[from + list.get(4)][1] + " ₩");
        binding.priceRecently6.setText(strarray[from + list.get(5)][1] + " ₩");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        mainFragment = new Main();
        load();

        Log.e("메인", "메인 들어옴");

        firebaseAuth = firebaseAuth.getInstance();


        //TODO : filter 애들도 binding 해서 세부사항 들어갈 수 있도록 묶어줘야 함

        //HomeFragment에서 FurnitureInfoFragment로 data 넘기기 위해 action 객체 만들어줌. 인자 순서대로 title, price, img
//        MainDirections.ActionNavigationHomeToFurnitureInfoFragment action =
//                MainDirections.actionNavigationHomeToFurnitureInfoFragment(strarray[list.get(0)][0],strarray[list.get(0)][1],strarray[list.get(0)][5],strarray[list.get(0)][2],strarray[list.get(0)][3]);
//        MainDirections.ActionNavigationHomeToFurnitureInfoFragment action2 =
//                MainDirections.actionNavigationHomeToFurnitureInfoFragment(strarray[list.get(1)][0],strarray[list.get(1)][1],strarray[list.get(1)][5],strarray[list.get(1)][2],strarray[list.get(1)][3]);
//        MainDirections.ActionNavigationHomeToFurnitureInfoFragment action3 =
//                MainDirections.actionNavigationHomeToFurnitureInfoFragment(strarray[list.get(2)][0],strarray[list.get(2)][1],strarray[list.get(2)][5],strarray[list.get(2)][2],strarray[list.get(2)][3]);

//        binding.cardRand1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(getView()).navigate(action);
//            }
//        });
//        binding.cardRand2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(getView()).navigate(action2);
//            }
//        });
//        binding.cardRand3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(getView()).navigate(action3);
//            }
//        });

        Button btn_more_likes = view.findViewById(R.id.btn_more_likes);
        HorizontalScrollView hsv = view.findViewById(R.id.horizontalScrollView);
        LinearLayout layout_more_likes = view.findViewById(R.id.layout_more_likes);
        btn_more_likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //filter
                Glide.with(getActivity()).load(strarray[list.get(0)][5]).into(binding.ivMoreLikes1);
                Glide.with(getActivity()).load(strarray[list.get(1)][5]).into(binding.ivMoreLikes2);
                Glide.with(getActivity()).load(strarray[list.get(2)][5]).into(binding.ivMoreLikes3);

                binding.tvMoreLikes1.setText(strarray[list.get(0)][0]);
                binding.tvMoreLikes2.setText(strarray[list.get(1)][0]);
                binding.tvMoreLikes3.setText(strarray[list.get(2)][0]);

                if(layout_more_likes.getVisibility() == View.GONE){
                    hsv.setVisibility(View.GONE);
                    layout_more_likes.setVisibility(View.VISIBLE);
                    btn_more_likes.setText("접기");
                }
                else{
                    layout_more_likes.setVisibility(View.GONE);
                    hsv.setVisibility(View.VISIBLE);
                    btn_more_likes.setText("더보기");
                }

            }
        });

        Button btn_more = view.findViewById(R.id.btn_more_products);
        LinearLayout layout_more = view.findViewById(R.id.layout_more_products);
        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //filter
                Glide.with(getActivity()).load(strarray[from + list.get(6)][5]).into(binding.filterImageMore1);
                Glide.with(getActivity()).load(strarray[from + list.get(7)][5]).into(binding.filterImageMore2);
                Glide.with(getActivity()).load(strarray[from + list.get(8)][5]).into(binding.filterImageMore3);
                Glide.with(getActivity()).load(strarray[from + list.get(9)][5]).into(binding.filterImageMore4);
                Glide.with(getActivity()).load(strarray[from + list.get(10)][5]).into(binding.filterImageMore5);
                Glide.with(getActivity()).load(strarray[from + list.get(11)][5]).into(binding.filterImageMore6);

                binding.titleRecentlyMore1.setText(strarray[from + list.get(6)][0]);
                binding.titleRecentlyMore2.setText(strarray[from + list.get(7)][0]);
                binding.titleRecentlyMore3.setText(strarray[from + list.get(8)][0]);
                binding.titleRecentlyMore4.setText(strarray[from + list.get(9)][0]);
                binding.titleRecentlyMore5.setText(strarray[from + list.get(10)][0]);
                binding.titleRecentlyMore6.setText(strarray[from + list.get(11)][0]);

                binding.priceRecentlyMore1.setText(strarray[from + list.get(6)][1] + " ₩");
                binding.priceRecentlyMore2.setText(strarray[from + list.get(7)][1] + " ₩");
                binding.priceRecentlyMore3.setText(strarray[from + list.get(8)][1] + " ₩");
                binding.priceRecentlyMore4.setText(strarray[from + list.get(9)][1] + " ₩");
                binding.priceRecentlyMore5.setText(strarray[from + list.get(10)][1] + " ₩");
                binding.priceRecentlyMore6.setText(strarray[from + list.get(11)][1] + " ₩");

                if(layout_more.getVisibility()==View.GONE){
                    layout_more.setVisibility(View.VISIBLE);
                    btn_more.setText("상품 접기");
                }
                else{
                    layout_more.setVisibility(View.GONE);
                    btn_more.setText("상품 더 보기");
                }

            }
        });

        Button st1 = view.findViewById(R.id.like_star_1);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users");

        st1.setOnClickListener(new View.OnClickListener() {
            Boolean flag = false;
            @Override
            public void onClick(View v) {

                String fur = strarray[list.get(0)][0];
                Toast.makeText(getContext(), fur, Toast.LENGTH_SHORT).show();
                Log.e("clicked text", fur);
                if(!flag){
                    flag = true;
                    st1.setTextColor(Color.YELLOW);
                    Log.e("star", "좋아요");
                    reference.child(uid).child("Likes").push().setValue(strarray[list.get(0)][0]);
                    String key = reference.child(uid).child("Likes").child(strarray[list.get(0)][0]).getKey();
                    Log.e("key", key);
                    //TODO : 내가 추가한 값에 대한 key 값을 받아오고 싶음
                }
                else {
                    flag = false;
                    //TODO : 별도 회색으로 가능한가?
                    st1.setTextColor(Color.GRAY);
                    Log.e("star", "해제");
                }

            }
        });

//        refresh 버튼 누르면 서버에 저장된 내 정보 불러오기
        //TODO : 필요한 정보만 불러와서 서버에 전달
        ImageButton refresh = view.findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(uid).child("Preference").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getContext(), "새로고침", Toast.LENGTH_SHORT).show();
                            Log.e("Preference", "Error getting data", task.getException());
                        }
                        else {
                            Log.d("Preference", String.valueOf(task.getResult().getValue()));
                        }
                    }
                });

                reference.child(uid).child("PreRate").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("PreRate", "Error getting data", task.getException());
                        }
                        else {
                            Log.d("PreRate", String.valueOf(task.getResult().getValue()));
                            preRate = String.valueOf(task.getResult().getValue());
                        }
                    }
                });

                reference.child(uid).child("Like").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("Like", "Error getting data", task.getException());
                        }
                        else {
                            Log.d("Like", String.valueOf(task.getResult().getValue()));
                            like = String.valueOf(task.getResult().getValue());
                        }
                    }
                });
            }
        });

        RadioButton all, bed, chair, closet, curtain, desk, lamp, shelf, sofa, table;
        RadioButton all2, bns, bnc, dns, dnc;
        RadioButton[] rb_arr, rb_arr2;

        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        RadioGroup radioGroup2 = view.findViewById(R.id.radioGroup2);
        all = view.findViewById(R.id.radio_all);
        sofa = view.findViewById(R.id.radio_sofa);
        table = view.findViewById(R.id.radio_table);
        closet = view.findViewById(R.id.radio_closet);
        chair = view.findViewById(R.id.radio_chair);
        bed = view.findViewById(R.id.radio_bed);
        shelf = view.findViewById(R.id.radio_shelf);
        lamp = view.findViewById(R.id.radio_lamp);
        desk = view.findViewById(R.id.radio_desk);
        curtain = view.findViewById(R.id.radio_curtain);
        all2 = view.findViewById(R.id.filter_all);
        bns = view.findViewById(R.id.filter_coldSoft);
        bnc = view.findViewById(R.id.filter_coldHard);
        dns = view.findViewById(R.id.filter_warmSoft);
        dnc = view.findViewById(R.id.filter_warmHard);

        rb_arr = new RadioButton[]{all, bed, chair, closet, curtain, desk, lamp, shelf, sofa, table};
        rb_arr2 = new RadioButton[]{all2, bns, bnc, dns, dnc};

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i=0; i<rb_arr.length; i++){
                    rb_arr[i].setTextColor(Color.parseColor("#4D455D"));
                }

                //TODO : 새로고침

                switch (checkedId){
                    case R.id.radio_all:
                        all.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "전체", Toast.LENGTH_SHORT).show();
                        n1 = 0;
                        break;
                    case R.id.radio_bed:
                        bed.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "침대", Toast.LENGTH_SHORT).show();
                        n1 = 1;
                        break;
                    case R.id.radio_chair:
                        chair.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "의자", Toast.LENGTH_SHORT).show();
                        n1 = 2;
                        break;
                    case R.id.radio_closet:
                        closet.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "옷장", Toast.LENGTH_SHORT).show();
                        n1 = 3;
                        break;
                    case R.id.radio_curtain:
                        curtain.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "커튼", Toast.LENGTH_SHORT).show();
                        n1 = 4;
                        break;
                    case R.id.radio_desk:
                        desk.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "책상", Toast.LENGTH_SHORT).show();
                        n1 = 5;
                        break;
                    case R.id.radio_lamp:
                        lamp.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "램프", Toast.LENGTH_SHORT).show();
                        n1 = 6;
                        break;
                    case R.id.radio_shelf:
                        shelf.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "선반", Toast.LENGTH_SHORT).show();
                        n1 = 7;
                        break;
                    case R.id.radio_sofa:
                        sofa.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "소파", Toast.LENGTH_SHORT).show();
                        n1 = 8;
                        break;
                    case R.id.radio_table:
                        table.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "테이블", Toast.LENGTH_SHORT).show();
                        n1 = 9;
                        break;
                }
                loadWithFilter(n1, n2);
            }
        });

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i=0; i<rb_arr2.length; i++){
                    rb_arr2[i].setTextColor(Color.parseColor("#4D455D"));
                }

                //TODO : 새로고침

                switch (checkedId){
                    case R.id.filter_all:
                        all2.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "전체", Toast.LENGTH_SHORT).show();
                        n2 = 0;
                        break;
                    case R.id.filter_coldSoft:
                        bns.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "bright&soft", Toast.LENGTH_SHORT).show();
                        n2 = 1;
                        break;
                    case R.id.filter_coldHard:
                        bnc.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "bright&clear", Toast.LENGTH_SHORT).show();
                        n2 = 2;
                        break;
                    case R.id.filter_warmSoft:
                        dns.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "deep&soft", Toast.LENGTH_SHORT).show();
                        n2 = 3;
                        break;
                    case R.id.filter_warmHard:
                        dnc.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "deep&clear", Toast.LENGTH_SHORT).show();
                        n2 = 4;
                        break;
                }
                loadWithFilter(n1, n2);
            }
        });

        return view;
    }

    public void adduser(String name, String gender) {

        //여기에서 직접 변수를 만들어서 값을 직접 넣는것도 가능합니다.
        // ex) int age=1; 등을 넣는 경우

        //user.java에서 선언했던 함수.
        user user = new user(name,gender);

        //child는 해당 키 위치로 이동하는 함수입니다.
        //키가 없는데 "user"와 name같이 값을 지정한 경우 자동으로 생성합니다.
        databaseReference.child("user").child(name).setValue(user);

    }

}