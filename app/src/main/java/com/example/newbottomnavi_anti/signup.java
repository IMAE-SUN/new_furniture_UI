package com.example.newbottomnavi_anti;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newbottomnavi_anti.databinding.FragmentMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Locale;

public class signup extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail, editTextPassword;
    RadioGroup RGGender;
    RadioButton RBGender;
    Button buttonSignup;
    String gender;
    FirebaseAuth firebaseAuth;

    private FragmentMainBinding binding;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Log.d("onCreate", "만들었을 때");

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.SignUpEmailEditText);
        editTextPassword = (EditText) findViewById(R.id.SignUpPasswordEditText);
        RGGender = (RadioGroup) findViewById(R.id.SignUpGenderRadioGroup);
        buttonSignup = (Button) findViewById(R.id.btnSignup);

        firebaseAuth = firebaseAuth.getInstance();

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
//                String email, pwd, gender;
//                if(editTextEmail.getText().toString()==""){
//
//                }
//                email = editTextEmail.getText().toString().trim();
//                pwd = editTextPassword.getText().toString().trim();
//                gender = editTextGender.getText().toString().trim();
//
//                firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener((Activity) getApplicationContext(), new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()){
//                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                        else{
//                            Toast.makeText(signup.this, "로그인 오류", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                    }
//                });
            }
        });

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        if(currentUser != null) {
//
//            ActionBar actionBar = getSupportActionBar();
//            actionBar.hide();
//
//            //initializing views
//
//            Log.d("registerUser", "등록하자 제발");
//            //사용자가 입력하는 email, password를 가져온다.
//
//
//
//            //button click event
//            buttonSignup.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.d("clickListener", "버튼 눌렀을 때");
//
//                    registerUser();
//                }
//            });
//        }
//        else{
//            Log.d("CurrentUser", "is NULL");
//        }



    }

    //Firebse creating a new user
    private void registerUser() {

//        adduser(editTextEmail.getText().toString(), editTextGender.getText().toString());
//        Toast.makeText(getApplicationContext(), "추가됐습니다", Toast.LENGTH_SHORT).show();

//        Intent intent = new Intent(signup.this, MainActivity.class);
//        startActivity(intent);
//        Log.d("RegisterUser", "들어옴");

        RBGender = findViewById(RGGender.getCheckedRadioButtonId());

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String gender = RBGender.getText().toString();

        Log.e("String email", email );
        Log.e("String pw", password);
        Log.e("String gender", gender);

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){


                    //email과 password가 비었는지 아닌지를 체크 한다.
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getApplicationContext(), "Email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(getApplicationContext(), "Password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(password.length() < 6){
                        Toast.makeText(getApplicationContext(), "Password 는 6자 이상이어야 합니다", Toast.LENGTH_SHORT).show();
                    }

                    if (TextUtils.isEmpty(gender)) {
                        Toast.makeText(getApplicationContext(), "Gender을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    String uid = user.getUid();

                    Toast.makeText(getApplicationContext(), uid, Toast.LENGTH_SHORT).show();

                    user = firebaseAuth.getCurrentUser();

                    HashMap<Object, String> hashMap = new HashMap<>();

                    hashMap.put("uid", uid);
                    hashMap.put("email", email);
                    hashMap.put("pw", password);
                    hashMap.put("gender", gender);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference("Users");
                    reference.child(uid).setValue(hashMap);

                    Intent intent = new Intent(signup.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    Log.e("SignUp", "돌아가기");
                }
            }
        });



    }

    public void adduser(String name, String gender) {

        //여기에서 직접 변수를 만들어서 값을 직접 넣는것도 가능합니다.
        // ex) int age=1; 등을 넣는 경우

        //user.java에서 선언했던 함수.
        user user = new user(name,gender);

        //child는 해당 키 위치로 이동하는 함수입니다.
        //키가 없는데 "user"와 name같이 값을 지정한 경우 자동으로 생성합니다.
        databaseReference.child("user").child(name).setValue(user);
        Log.d("signup", "database 올라감");

    }

    @Override
    public void onClick(View view) {
        registerUser();
    }

}