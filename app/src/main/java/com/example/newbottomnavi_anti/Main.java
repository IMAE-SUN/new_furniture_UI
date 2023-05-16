package com.example.newbottomnavi_anti;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.newbottomnavi_anti.databinding.FragmentMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main extends Fragment {

    Main mainFragment;
    private FragmentMainBinding binding;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();
    FirebaseAuth firebaseAuth;

    public Main() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        mainFragment = new Main();

        Log.e("메인", "메인 들어옴");

        firebaseAuth = firebaseAuth.getInstance();

        //침대 40개, 책상 36개, 소파36개
        String [][] strarray = new String[112][6];

        try {
            BufferedReader reader = new BufferedReader(new FileReader("/data/data/com.example.newbottomnavi_anti/files/furniture.txt"));
            String line;

            int i = 0;
            while ((line = reader.readLine()) != null) {
                strarray[i] = line.split(";");
                i++;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e ) {
            e.printStackTrace();
        }

        //0~39까지의 중복 없는 난수 11개 생성
        Set<Integer> set = new HashSet<>();

        while (set.size() < 11) {
            Double d = Math.random() * 112;
            set.add(d.intValue());
        }

        List<Integer> list = new ArrayList<>(set);

        //random
        Glide.with(getActivity()).load(strarray[list.get(0)][5]).into(binding.bannerA1);
        Glide.with(getActivity()).load(strarray[list.get(1)][5]).into(binding.bannerB1);
        Glide.with(getActivity()).load(strarray[list.get(2)][5]).into(binding.bannerC1);

        //trending
        Glide.with(getActivity()).load(strarray[list.get(3)][5]).into(binding.imgTrending1);
        Glide.with(getActivity()).load(strarray[list.get(4)][5]).into(binding.imgTrending2);
        Glide.with(getActivity()).load(strarray[list.get(5)][5]).into(binding.imgTrending3);
        Glide.with(getActivity()).load(strarray[list.get(6)][5]).into(binding.imgTrending4);

        //recently
        Glide.with(getActivity()).load(strarray[list.get(7)][5]).into(binding.imgRecently1);
        Glide.with(getActivity()).load(strarray[list.get(8)][5]).into(binding.imgRecently2);
        Glide.with(getActivity()).load(strarray[list.get(9)][5]).into(binding.imgRecently3);
        Glide.with(getActivity()).load(strarray[list.get(10)][5]).into(binding.imgRecently4);


        binding.rand1Title.setText(strarray[list.get(0)][0]);
        binding.rand2Title.setText(strarray[list.get(1)][0]);
        binding.rand3Title.setText(strarray[list.get(2)][0]);

        binding.rand1Price.setText(strarray[list.get(0)][1] +" ₩");
        binding.rand2Price.setText(strarray[list.get(1)][1] +" ₩");
        binding.rand3Price.setText(strarray[list.get(2)][1] +" ₩");

        //HomeFragment에서 FurnitureInfoFragment로 data 넘기기 위해 action 객체 만들어줌. 인자 순서대로 title, price, img
        MainDirections.ActionNavigationHomeToFurnitureInfoFragment action =
                MainDirections.actionNavigationHomeToFurnitureInfoFragment(strarray[list.get(0)][0],strarray[list.get(0)][1],strarray[list.get(0)][5],strarray[list.get(0)][2],strarray[list.get(0)][3]);
        MainDirections.ActionNavigationHomeToFurnitureInfoFragment action2 =
                MainDirections.actionNavigationHomeToFurnitureInfoFragment(strarray[list.get(1)][0],strarray[list.get(1)][1],strarray[list.get(1)][5],strarray[list.get(1)][2],strarray[list.get(1)][3]);
        MainDirections.ActionNavigationHomeToFurnitureInfoFragment action3 =
                MainDirections.actionNavigationHomeToFurnitureInfoFragment(strarray[list.get(2)][0],strarray[list.get(2)][1],strarray[list.get(2)][5],strarray[list.get(2)][2],strarray[list.get(2)][3]);

        binding.cardRand1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(action);
            }
        });
        binding.cardRand2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(action2);
            }
        });
        binding.cardRand3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(action3);
            }
        });


        Button st1 = view.findViewById(R.id.star_1);
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
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                        }
                    }
                });
            }
        });

        RadioButton all, sofa, liveTable, kitTable, bed, chair, shelf, study, kids, interior;
        RadioButton all2, bns, bnc, dns, dnc;
        RadioButton[] rb_arr, rb_arr2;

        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        RadioGroup radioGroup2 = view.findViewById(R.id.radioGroup2);
        all = view.findViewById(R.id.radio_all);
        sofa = view.findViewById(R.id.radio_sofa);
        liveTable = view.findViewById(R.id.radio_liveTable);
        kitTable = view.findViewById(R.id.radio_kitTable);
        chair = view.findViewById(R.id.radio_chair);
        bed = view.findViewById(R.id.radio_bed);
        shelf = view.findViewById(R.id.radio_shelf);
        study = view.findViewById(R.id.radio_study);
        kids = view.findViewById(R.id.radio_kids);
        interior = view.findViewById(R.id.radio_interior);
        all2 = view.findViewById(R.id.filter_all);
        bns = view.findViewById(R.id.filter_bright_soft);
        bnc = view.findViewById(R.id.filter_bright_clear);
        dns = view.findViewById(R.id.filter_deep_soft);
        dnc = view.findViewById(R.id.filter_deep_clear);

        rb_arr = new RadioButton[]{all, sofa, liveTable, kitTable, chair, bed, shelf, study, kids, interior};
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
                        break;
                    case R.id.radio_sofa:
                        sofa.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "소파", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio_liveTable:
                        liveTable.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "거실테이블", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio_kitTable:
                        kitTable.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "식탁", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio_bed:
                        bed.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "침대", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio_shelf:
                        shelf.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "선반", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio_study:
                        study.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "공부용", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio_kids:
                        kids.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "아이용", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio_interior:
                        interior.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "인테리어용", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio_chair:
                        chair.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "의자", Toast.LENGTH_SHORT).show();
                        break;
                }
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
                        break;
                    case R.id.filter_bright_soft:
                        bns.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "bright&soft", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.filter_bright_clear:
                        bnc.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "bright&clear", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.filter_deep_soft:
                        dns.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "deep&soft", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.filter_deep_clear:
                        dnc.setTextColor(Color.parseColor("#FEFCF3"));
                        Toast.makeText(getContext(), "deep&clear", Toast.LENGTH_SHORT).show();
                        break;

                }
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