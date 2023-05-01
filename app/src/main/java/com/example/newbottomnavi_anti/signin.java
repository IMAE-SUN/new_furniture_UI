package com.example.newbottomnavi_anti;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newbottomnavi_anti.databinding.FragmentMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class signin extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail, editTextPassword, editTextGender;
    Button buttonSignup, buttonSignin;
    FirebaseAuth firebaseAuth;

    private FragmentMainBinding binding;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Log.d("onCreate", "만들었을 때");

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.SignInEmailEditText);
        editTextPassword = (EditText) findViewById(R.id.SignInPasswordEditText);
        buttonSignup = (Button) findViewById(R.id.btnSignup);
        buttonSignin = (Button) findViewById(R.id.btnSignin);

        firebaseAuth = firebaseAuth.getInstance();

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signin.this, signup.class);
                Toast.makeText(signin.this, "회원가입으로 이동합니다", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });


        buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String pwd = editTextPassword.getText().toString().trim();

                firebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener((Activity) getApplicationContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.e("login", "로그인 성공");
                            Toast.makeText(signin.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Log.e("login", "로그인 실패");
                            Toast.makeText(signin.this, "로그인 오류", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    //Firebse creating a new user
    private void registerUser() {

        adduser(editTextEmail.getText().toString(), editTextGender.getText().toString());
        Toast.makeText(getApplicationContext(), "추가됐습니다", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(signin.this, MainActivity.class);
        startActivity(intent);
//        Log.d("RegisterUser", "들어옴");
//
//
//        String email = editTextEmail.getText().toString().trim();
//        String password = editTextPassword.getText().toString().trim();
//        String nickName = editTextNickname.getText().toString().trim();
//
//        Log.e("String email", email );
//        Log.e("String pw", password);
//
//        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//
//
//                    //email과 password가 비었는지 아닌지를 체크 한다.
//                    if (TextUtils.isEmpty(email)) {
//                        Toast.makeText(getApplicationContext(), "Email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    if (TextUtils.isEmpty(password)) {
//                        Toast.makeText(getApplicationContext(), "Password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    if (TextUtils.isEmpty(nickName)) {
//                        Toast.makeText(getApplicationContext(), "NickName를 입력해 주세요.", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//
//                    FirebaseUser user = firebaseAuth.getCurrentUser();
//
//                    String uid = user.getUid();
//
//                    Toast.makeText(getApplicationContext(), uid, Toast.LENGTH_SHORT).show();
//
//                    user = firebaseAuth.getCurrentUser();
//
//                    HashMap<Object, String> hashMap = new HashMap<>();
//
//                    hashMap.put("uid", uid);
//                    hashMap.put("email", email);
//                    hashMap.put("pw", password);
//                    hashMap.put("nickName", nickName);
//
//                    FirebaseDatabase database = FirebaseDatabase.getInstance();
//                    DatabaseReference reference = database.getReference("Users");
//                    reference.child(uid).setValue(hashMap);
//
//                    Intent intent = new Intent(signup.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                    Log.e("SignUp", "돌아가기");
//                }
//            }
//        });



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