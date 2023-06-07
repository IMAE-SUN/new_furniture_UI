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
        strarray = new String[112][7];
        int max, min;
        max = 4;
        min = 1;
        int mood_int = (int) Math.random() * (max-min+1) + 1;
        String mood_str = Integer.toString(mood_int);

        AssetManager assetManager = getActivity().getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("furniture.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            int i = 0;
            while ((line = reader.readLine()) != null) {
                line = line.concat(";" + mood_str);
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

        while (set.size() < 12) {
            Double d = Math.random() * 112;
            set.add(d.intValue());
        }

        list = new ArrayList<>(set);

        //filter
        Glide.with(getActivity()).load(strarray[list.get(0)][5]).into(binding.filterImage1);
        Glide.with(getActivity()).load(strarray[list.get(1)][5]).into(binding.filterImage2);
        Glide.with(getActivity()).load(strarray[list.get(2)][5]).into(binding.filterImage3);
        Glide.with(getActivity()).load(strarray[list.get(3)][5]).into(binding.filterImage4);
        Glide.with(getActivity()).load(strarray[list.get(4)][5]).into(binding.filterImage5);
        Glide.with(getActivity()).load(strarray[list.get(5)][5]).into(binding.filterImage6);

        //like
        Glide.with(getActivity()).load(strarray[list.get(0)][5]).into(binding.likeImage1);
        Glide.with(getActivity()).load(strarray[list.get(1)][5]).into(binding.likeImage2);
        Glide.with(getActivity()).load(strarray[list.get(2)][5]).into(binding.likeImage3);


        binding.titleRecently1.setText(strarray[list.get(0)][0] + " ₩");
        binding.titleRecently2.setText(strarray[list.get(1)][0] + " ₩");
        binding.titleRecently3.setText(strarray[list.get(2)][0] + " ₩");
        binding.titleRecently4.setText(strarray[list.get(3)][0] + " ₩");
        binding.titleRecently5.setText(strarray[list.get(4)][0] + " ₩");
        binding.titleRecently6.setText(strarray[list.get(5)][0] + " ₩");

        binding.priceRecently1.setText(strarray[list.get(0)][1] + " ₩");
        binding.priceRecently2.setText(strarray[list.get(1)][1] + " ₩");
        binding.priceRecently3.setText(strarray[list.get(2)][1] + " ₩");
        binding.priceRecently4.setText(strarray[list.get(3)][1] + " ₩");
        binding.priceRecently5.setText(strarray[list.get(4)][1] + " ₩");
        binding.priceRecently6.setText(strarray[list.get(5)][1] + " ₩");

        binding.likeTitle1.setText(strarray[list.get(0)][0]);
        binding.likeTitle2.setText(strarray[list.get(1)][0]);
        binding.likeTitle3.setText(strarray[list.get(2)][0]);

        binding.likePrice1.setText(strarray[list.get(0)][1] +" ₩");
        binding.likePrice2.setText(strarray[list.get(1)][1] +" ₩");
        binding.likePrice3.setText(strarray[list.get(2)][1] +" ₩");



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

        Button btn_more = view.findViewById(R.id.btn_more_products);
        LinearLayout layout_more = view.findViewById(R.id.layout_more_products);
        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //filter
                Glide.with(getActivity()).load(strarray[list.get(6)][5]).into(binding.filterImageMore1);
                Glide.with(getActivity()).load(strarray[list.get(7)][5]).into(binding.filterImageMore2);
                Glide.with(getActivity()).load(strarray[list.get(8)][5]).into(binding.filterImageMore3);
                Glide.with(getActivity()).load(strarray[list.get(9)][5]).into(binding.filterImageMore4);
                Glide.with(getActivity()).load(strarray[list.get(10)][5]).into(binding.filterImageMore5);
                Glide.with(getActivity()).load(strarray[list.get(11)][5]).into(binding.filterImageMore6);

                binding.titleRecentlyMore1.setText(strarray[list.get(6)][0] + " ₩");
                binding.titleRecentlyMore2.setText(strarray[list.get(7)][0] + " ₩");
                binding.titleRecentlyMore3.setText(strarray[list.get(8)][0] + " ₩");
                binding.titleRecentlyMore4.setText(strarray[list.get(9)][0] + " ₩");
                binding.titleRecentlyMore5.setText(strarray[list.get(10)][0] + " ₩");
                binding.titleRecentlyMore6.setText(strarray[list.get(11)][0] + " ₩");

                binding.priceRecentlyMore1.setText(strarray[list.get(6)][1] + " ₩");
                binding.priceRecentlyMore2.setText(strarray[list.get(7)][1] + " ₩");
                binding.priceRecentlyMore3.setText(strarray[list.get(8)][1] + " ₩");
                binding.priceRecentlyMore4.setText(strarray[list.get(9)][1] + " ₩");
                binding.priceRecentlyMore5.setText(strarray[list.get(10)][1] + " ₩");
                binding.priceRecentlyMore6.setText(strarray[list.get(11)][1] + " ₩");

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