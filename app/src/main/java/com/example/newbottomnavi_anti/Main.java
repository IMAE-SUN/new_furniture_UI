package com.example.newbottomnavi_anti;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.newbottomnavi_anti.databinding.FragmentMainBinding;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main extends Fragment {

    int[] images = new int[] {R.drawable.ic_baseline_electric_bike, R.drawable.ic_baseline_emoji_emotions, R.drawable.ic_baseline_home, R.drawable.ic_baseline_settings};

    Main mainFragment;
    private FragmentMainBinding binding;

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

        //0~39까지의 중복 없는 난수 4개 생성
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
                MainDirections.actionNavigationHomeToFurnitureInfoFragment(strarray[list.get(0)][0],strarray[list.get(0)][1],strarray[list.get(0)][5]);
        MainDirections.ActionNavigationHomeToFurnitureInfoFragment action2 =
                MainDirections.actionNavigationHomeToFurnitureInfoFragment(strarray[list.get(1)][0],strarray[list.get(1)][1],strarray[list.get(1)][5]);
        MainDirections.ActionNavigationHomeToFurnitureInfoFragment action3 =
                MainDirections.actionNavigationHomeToFurnitureInfoFragment(strarray[list.get(2)][0],strarray[list.get(2)][1],strarray[list.get(2)][5]);

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

        return view;
    }

//    public void onClick(View v){
//
//        ImageView frameimg = v.findViewById(R.id.frameimg);
//
//        switch (v.getId()) {
//            case R.id.card_rand1:
//                Glide.with(getActivity()).load("https://img.danawa.com/prod_img/500000/412/126/img/11126412_1.jpg?shrink=130:130&_v=20220527163310").into(frameimg);
//                break;
//        }
//    }
}