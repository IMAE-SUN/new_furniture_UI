package com.example.newbottomnavi_anti;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class PreRate extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prerate);

        Log.e("PreRate", "시작");
        CheckBox cb1 = findViewById(R.id.cb_ikea_1);
        CheckBox cb2 = findViewById(R.id.cb_ikea_2);
        CheckBox cb3 = findViewById(R.id.cb_ikea_3);
        CheckBox cb4 = findViewById(R.id.cb_ikea_4);
        CheckBox cb5 = findViewById(R.id.cb_ikea_5);
        CheckBox cb6 = findViewById(R.id.cb_ikea_6);
        Button submit = findViewById(R.id.submit);

        firebaseAuth = firebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String total_pick_string = "";

                if(cb1.isChecked()){
                    total_pick_string += cb1.getText().toString() + " ";
                }
                if(cb2.isChecked()){
                    total_pick_string += cb2.getText().toString() + " ";
                }
                if(cb3.isChecked()){
                    total_pick_string += cb3.getText().toString() + " ";
                }
                if(cb4.isChecked()){
                    total_pick_string += cb4.getText().toString() + " ";
                }
                if(cb5.isChecked()){
                    total_pick_string += cb5.getText().toString() + " ";
                }
                if(cb6.isChecked()){
                    total_pick_string += cb6.getText().toString() + " ";
                }

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
        });
    }
}
