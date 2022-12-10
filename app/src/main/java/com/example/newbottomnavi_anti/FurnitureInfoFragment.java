package com.example.newbottomnavi_anti;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.newbottomnavi_anti.databinding.FragmentFurnitureInfoBinding;

public class FurnitureInfoFragment extends Fragment {

    private FragmentFurnitureInfoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFurnitureInfoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //HomeFragment에서 넘긴 data 받아오기
        String title = FurnitureInfoFragmentArgs.fromBundle(getArguments()).getTitle();
        binding.title.setText(title);

        String price = FurnitureInfoFragmentArgs.fromBundle(getArguments()).getPrice();
        binding.price.setText(price+" 원");

        String img = FurnitureInfoFragmentArgs.fromBundle(getArguments()).getImg();
        Glide.with(getActivity()).load(img).into(binding.img);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}