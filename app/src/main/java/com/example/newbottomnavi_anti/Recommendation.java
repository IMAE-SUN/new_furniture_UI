package com.example.newbottomnavi_anti;

import static android.app.Activity.RESULT_OK;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.newbottomnavi_anti.databinding.FragmentMainBinding;
import com.example.newbottomnavi_anti.databinding.FragmentRecommendationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Recommendation extends Fragment {

    public Recommendation() {
        // Required empty public constructor
    }
    Recommendation recomFragment;
    private FragmentRecommendationBinding binding;
    FirebaseAuth firebaseAuth;
    String RadioText, RadioText2;
    ImageView img_wys_1, img_wys_2, img_wys_3, img_wys_4, img_recom_a, img_recom_a_1, img_recom_b, img_recom_b_1, img_recom_c, img_recom_c_1, img_recom_d, img_recom_d_1;
    Button recommend_btn;
    ImageButton refresh_btn;
    LinearLayout recommend_layout;
    String selectedCbText = NULL;
    String [][] strarray;
    List<Integer> list;
    int sum = 0;
    int max = 4;

    private static final int MY_PERMISSION_CAMERA = 1111;
    private static final int REQUEST_TAKE_PHOTO = 2222;
    private static final int REQUEST_TAKE_ALBUM = 3333;
    private static final int REQUEST_IMAGE_CROP = 4444;

    String mCurrentPhotoPath;

    Uri imageUri;
    Uri photoURI, albumURI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_recommendation, container, false);

        binding = FragmentRecommendationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        recomFragment = new Recommendation();

        Log.e("추천", "추천들어옴");


        RadioGroup radiogroup = view.findViewById(R.id.radiogroup);
        RadioButton rb1 = view.findViewById(R.id.radiobutton1);
        RadioButton rb2 = view.findViewById(R.id.radiobutton2);
        RadioButton rb3 = view.findViewById(R.id.radiobutton3);
        RadioButton rb4 = view.findViewById(R.id.radiobutton4);

        RadioGroup radioGroup2 = view.findViewById(R.id.seekbar_radioGroup);
        RadioButton rb5 = view.findViewById(R.id._seekbar_1_randioButton);
        RadioButton rb6 = view.findViewById(R.id._seekbar_2_randioButton);
        RadioButton rb7 = view.findViewById(R.id._seekbar_3_randioButton);
        RadioButton rb8 = view.findViewById(R.id._seekbar_4_randioButton);
        RadioButton rb9 = view.findViewById(R.id._seekbar_5_randioButton);

        //HomeFragment에서 FurnitureInfoFragment로 data 넘기기 위해 action 객체 만들어줌. 인자 순서대로 title, price, img


        /**
         * <TODO>
         *     이부분 null떠서 주석해놨음. 기존 furniture.txt에서 txt파일이 바뀌어서
         *     PreRate.java의 load()처럼 여러 txt파일 읽어오는식으로 바꿔야함!
         * </TODO>
         *
         */
//        MainDirections.ActionNavigationHomeToFurnitureInfoFragment action =
//                MainDirections.actionNavigationHomeToFurnitureInfoFragment(strarray[list.get(0)][0],strarray[list.get(0)][1],strarray[list.get(0)][5],strarray[list.get(0)][2],strarray[list.get(0)][3]);
//        MainDirections.ActionNavigationHomeToFurnitureInfoFragment action2 =
//                MainDirections.actionNavigationHomeToFurnitureInfoFragment(strarray[list.get(1)][0],strarray[list.get(1)][1],strarray[list.get(1)][5],strarray[list.get(1)][2],strarray[list.get(1)][3]);
//        MainDirections.ActionNavigationHomeToFurnitureInfoFragment action3 =
//                MainDirections.actionNavigationHomeToFurnitureInfoFragment(strarray[list.get(2)][0],strarray[list.get(2)][1],strarray[list.get(2)][5],strarray[list.get(2)][2],strarray[list.get(2)][3]);
//        MainDirections.ActionNavigationHomeToFurnitureInfoFragment action4 =
//                MainDirections.actionNavigationHomeToFurnitureInfoFragment(strarray[list.get(3)][0],strarray[list.get(3)][1],strarray[list.get(3)][5],strarray[list.get(3)][2],strarray[list.get(3)][3]);

//        binding.recomCardA.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(getView()).navigate(action);
//            }
//        });
//        binding.recomCardB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(getView()).navigate(action2);
//            }
//        });
//        binding.recomCardC.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(getView()).navigate(action3);
//            }
//        });
//        binding.recomCardD.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(getView()).navigate(action4);
//            }
//        });


//        Radio Button 처리
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

//                String RadioText;
                switch (checkedId){
                    case R.id.radiobutton1:
                        Toast.makeText(getContext(), rb1.getText().toString(), Toast.LENGTH_SHORT).show();
                        Log.e("Radio Button1", rb1.getText().toString());
                        RadioText = rb1.getText().toString();
                        break;
                    case R.id.radiobutton2:
                        Toast.makeText(getContext(), rb2.getText().toString(), Toast.LENGTH_SHORT).show();
                        Log.e("Radio Button2", rb2.getText().toString());
                        RadioText = rb2.getText().toString();
                        break;
                    case R.id.radiobutton3:
                        Toast.makeText(getContext(), rb3.getText().toString(), Toast.LENGTH_SHORT).show();
                        Log.e("Radio Button3", rb3.getText().toString());
                        RadioText = rb3.getText().toString();
                        break;
                    case R.id.radiobutton4:
                        Toast.makeText(getContext(), rb4.getText().toString(), Toast.LENGTH_SHORT).show();
                        Log.e("Radio Button3", rb4.getText().toString());
                        RadioText = rb4.getText().toString();
                        break;
                }
            }
        });

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

//                String RadioText;
                switch (checkedId){
                    case R.id._seekbar_1_randioButton:
                        Toast.makeText(getContext(), rb5.getText().toString(), Toast.LENGTH_SHORT).show();
                        Log.e("Radio Button1", rb5.getText().toString());
                        RadioText2 = rb5.getText().toString();
                        break;
                    case R.id._seekbar_2_randioButton:
                        Toast.makeText(getContext(), rb6.getText().toString(), Toast.LENGTH_SHORT).show();
                        Log.e("Radio Button2", rb6.getText().toString());
                        RadioText2 = rb6.getText().toString();
                        break;
                    case R.id._seekbar_3_randioButton:
                        Toast.makeText(getContext(), rb7.getText().toString(), Toast.LENGTH_SHORT).show();
                        Log.e("Radio Button3", rb7.getText().toString());
                        RadioText2 = rb7.getText().toString();
                        break;
                    case R.id._seekbar_4_randioButton:
                        Toast.makeText(getContext(), rb8.getText().toString(), Toast.LENGTH_SHORT).show();
                        Log.e("Radio Button3", rb8.getText().toString());
                        RadioText2 = rb8.getText().toString();
                        break;
                    case R.id._seekbar_5_randioButton:
                        Toast.makeText(getContext(), rb9.getText().toString(), Toast.LENGTH_SHORT).show();
                        Log.e("Radio Button3", rb9.getText().toString());
                        RadioText2 = rb9.getText().toString();
                        break;
                }
            }
        });

        CheckBox cb1 = view.findViewById(R.id.cb_question_2_bed);
        CheckBox cb2 = view.findViewById(R.id.cb_question_2_chair);
        CheckBox cb3 = view.findViewById(R.id.cb_question_2_child);
        CheckBox cb4 = view.findViewById(R.id.cb_question_2_dining);
        CheckBox cb5 = view.findViewById(R.id.cb_question_2_interior);
        CheckBox cb6 = view.findViewById(R.id.cb_question_2_shelf);
        CheckBox cb7 = view.findViewById(R.id.cb_question_2_sofa);
        CheckBox cb8 = view.findViewById(R.id.cb_question_2_sofatable);
        CheckBox cb9 = view.findViewById(R.id.cb_question_2_study);


//        카메라, 갤러리 버튼
//        TODO : 연결하기
        Button camera_btn = view.findViewById(R.id.camera_recom);
        Button gallery_btn = view.findViewById(R.id.gallery_recom);
        Button more_opt = view.findViewById(R.id.btn_more_options);
        LinearLayout more_opt_layout = view.findViewById(R.id.lo_more_options);

//        그렇게 불러온 사진들 띄워주기 (최대 4개)
//        TODO : 클릭 시 없어져야 함
        img_wys_1 = view.findViewById(R.id.img_whatsyourstle_1);
        img_wys_2 = view.findViewById(R.id.img_whatsyourstle_2);
        img_wys_3 = view.findViewById(R.id.img_whatsyourstle_3);
        img_wys_4 = view.findViewById(R.id.img_whatsyourstle_4);

//        추천하기 버튼
//        TODO : 클릭 시 python 코드와 연결되도록
        recommend_btn = view.findViewById(R.id.recommend_btn);
        recommend_layout = view.findViewById(R.id.recommendation_layout);

//        refresh 버튼
//        TODO : 클릭 시 기존 정보 바탕으로 (연산 x) 다시 추천
        refresh_btn = view.findViewById(R.id.refresh_imgbtn);

//        추천해주는 제품들 사진
//        a, b, c, d 가 각 제품
//        _1, _2 이런게 그 속에 속하는 부 사진들
        img_recom_a = view.findViewById(R.id.img_recom_a);
        img_recom_a_1 = view.findViewById(R.id.img_recom_a_1);
        img_recom_b = view.findViewById(R.id.img_recom_b);
        img_recom_b_1 = view.findViewById(R.id.img_recom_b_1);
        img_recom_c = view.findViewById(R.id.img_recom_c);
        img_recom_c_1 = view.findViewById(R.id.img_recom_c_1);
        img_recom_d = view.findViewById(R.id.img_recom_d);
        img_recom_d_1 = view.findViewById(R.id.img_recom_d_1);

        more_opt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(more_opt_layout.getVisibility() == View.GONE){
                    more_opt_layout.setVisibility(View.VISIBLE);
                }else{
                    more_opt_layout.setVisibility(View.GONE);
                }
            }
        });

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(getContext(), "권한 허가", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getContext(), "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();

            }
        };

//        사진찍기
        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("camera", "camera 클릭함");
//                captureCamera();
            }
        });


//        갤러리에서 가져오기
        gallery_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("gallery", "gallery 클릭함");
                getAlbum();
            }
        });

//        checkPermission();

//        추천하기 (파이썬과 연결)
        recommend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("recommedation", "recommendation 시작함");
                if(recommend_layout.getVisibility() == View.GONE){
                    recommend_layout.setVisibility(View.VISIBLE);
                }

                Toast.makeText(getContext(), RadioText, Toast.LENGTH_SHORT).show();
                Log.e("Final Radio Button", RadioText);


                if(cb1.isChecked()){
                    selectedCbText += cb1.getText().toString() + " ";
                }
                if(cb2.isChecked()){
                    selectedCbText += cb2.getText().toString() + " ";
                }
                if(cb3.isChecked()){
                    selectedCbText += cb3.getText().toString() + " ";
                }
                if(cb4.isChecked()){
                    selectedCbText += cb4.getText().toString() + " ";
                }
                if(cb5.isChecked()){
                    selectedCbText += cb5.getText().toString() + " ";
                }
                if(cb6.isChecked()){
                    selectedCbText += cb6.getText().toString() + " ";
                }
                if(cb7.isChecked()){
                    selectedCbText += cb7.getText().toString() + " ";
                }
                if(cb8.isChecked()){
                    selectedCbText += cb8.getText().toString() + " ";
                }
                if(cb9.isChecked()){
                    selectedCbText += cb9.getText().toString() + " ";
                }

                Toast.makeText(getContext(), selectedCbText, Toast.LENGTH_SHORT).show();
                Log.e("Final CheckBox", selectedCbText);

                if (selectedCbText==NULL){
                    Toast.makeText(getContext(), "모든 옵션을 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    connect();
                }
            }
        });

////        추천 새로고침
//        refresh_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("refresh", "refresh 클릭함");
//                Toast.makeText(getContext(), "refresh", Toast.LENGTH_SHORT).show();
//            }
//        });

//       Glide.with(this).load("https://pix8.agoda.net/hotelImages/111/1110567/1110567_16083113590045958402.jpg?ca=6&ce=1&s=1024x768").placeholder(R.drawable.ic_baseline_emoji_emotions).into(img_wys_1);
//       Glide.with(this).load("https://pix8.agoda.net/hotelImages/111/1110567/1110567_16083113130045955510.jpg?ca=6&ce=1&s=1024x768").placeholder(R.drawable.ic_baseline_emoji_emotions).into(img_wys_2);


        Glide.with(this).load("https://pix8.agoda.net/hotelImages/111/1110567/1110567_17020812280050853336.jpg?ca=6&ce=1&s=1024x768").placeholder(R.drawable.ic_baseline_emoji_emotions).into(img_recom_a);
        Glide.with(this).load("https://q-xx.bstatic.com/xdata/images/hotel/840x460/58416127.jpg?k=0b4774030b77e017dc0b8a587f392f9dfab4283e3652a5ef357ccaee4d292ba7&o=").placeholder(R.drawable.ic_baseline_emoji_emotions).into(img_recom_a_1);
        Glide.with(this).load("https://img.danawa.com/prod_img/500000/043/638/img/1638043_1.jpg?shrink=500:500").placeholder(R.drawable.ic_baseline_emoji_emotions).into(img_recom_b);
        Glide.with(this).load("https://img.danawa.com/prod_img/500000/043/638/img/1638043_3.jpg?shrink=500:500").placeholder(R.drawable.ic_baseline_emoji_emotions).into(img_recom_b_1);
        Glide.with(this).load("https://img.danawa.com/prod_img/500000/063/627/img/4627063_1.jpg?shrink=500:500").placeholder(R.drawable.ic_baseline_emoji_emotions).into(img_recom_c);
        Glide.with(this).load("https://img.danawa.com/prod_img/500000/063/627/img/4627063_3.jpg?shrink=500:500").placeholder(R.drawable.ic_baseline_emoji_emotions).into(img_recom_c_1);
        Glide.with(this).load("https://img.danawa.com/prod_img/500000/392/235/img/5235392_1.jpg?shrink=500:500").placeholder(R.drawable.ic_baseline_emoji_emotions).into(img_recom_d);
        Glide.with(this).load("https://img.danawa.com/prod_img/500000/392/235/img/5235392_3.jpg?shrink=500:500").placeholder(R.drawable.ic_baseline_emoji_emotions).into(img_recom_d_1);

        return view;
    }
//        카메라 계속 오류나서 잠시 주석처리 함
//    private void captureCamera(){
//        String state = Environment.getExternalStorageState();
//        // 외장 메모리 검사
//        if (Environment.MEDIA_MOUNTED.equals(state)) {
//            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//                File photoFile = null;
//                try {
//                    photoFile = createImageFile();
//                } catch (IOException ex) {
//                    Log.e("captureCamera Error", ex.toString());
//                }
//                if (photoFile != null) {
//                    // getUriForFile의 두 번째 인자는 Manifest provier의 authorites와 일치해야 함
//
//                    Uri providerURI = FileProvider.getUriForFile(getContext(), getActivity().getPackageName(), photoFile);
//                    imageUri = providerURI;
//
//                    // 인텐트에 전달할 때는 FileProvier의 Return값인 content://로만!!, providerURI의 값에 카메라 데이터를 넣어 보냄
//                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI);
//
//                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//                }
//            }
//        } else {
//            Toast.makeText(getContext(), "저장공간이 접근 불가능한 기기입니다", Toast.LENGTH_SHORT).show();
//            return;
//        }
//    }
//
//    public File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + ".jpg";
//        File imageFile = null;
//        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "gyeom");
//
//        if (!storageDir.exists()) {
//            Log.i("mCurrentPhotoPath1", storageDir.toString());
//            storageDir.mkdirs();
//        }
//
//        imageFile = new File(storageDir, imageFileName);
//        mCurrentPhotoPath = imageFile.getAbsolutePath();
//
//        return imageFile;
//    }


    private void getAlbum(){
        Log.i("getAlbum", "Call");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    img_wys_1.setImageURI(uri);
                }
                break;
        }
    }


    private final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    private Handler mHandler;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private String ip = "210.102.178.118";
    private int port = 12125;
    private String img_path;
    private Bitmap img;

    public void connect() { // 서버 socketHost.py와 연결

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users");

//                분위기 전달
        reference.child(uid).child("Preference").child("Mood").setValue(RadioText);

//                가구 전달
        HashMap<Object, String> hashMap = new HashMap<>();
        String[] temp = selectedCbText.split(" ");
        int n = temp.length;
        for (int i=0; i<n; i++){
            Log.e("CheckBox", temp[i]);
            hashMap.put("furniture", temp[i]);
            reference.child(uid).child("Preference").child("Furniture").push().setValue(hashMap);
        }

//                퍼센트 전달
        reference.child(uid).child("Preference").child("seekbar").setValue(RadioText2);



        mHandler = new Handler();
        BitmapDrawable drawable = (BitmapDrawable) img_wys_1.getDrawable();
        img = drawable.getBitmap();
        Log.w("connect", "연결 하는중");
        Thread checkUpdate = new Thread() {
            public void run() {
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                byte[] bytes = byteArray.toByteArray();
                String color = new String();
                String histo_similarity = new String();
                int data_len=0;
                byte[] img_result;
                // 서버 접속
                try {
                    socket = new Socket(ip, port);
                    Log.w("서버:", "서버 접속됨");
                } catch (IOException e1) {
                    Log.w("서버:", "서버접속못함");
                    e1.printStackTrace();
                }

                Log.w(": ", "안드로이드에서 서버로 연결요청");

                try {
                    dos = new DataOutputStream(socket.getOutputStream());
                    dis = new DataInputStream(socket.getInputStream());

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("버퍼:", "버퍼생성 잘못됨");
                }
                Log.w("버퍼:", "버퍼생성 잘됨");

                try {
                    dos.writeUTF(Integer.toString(bytes.length));
                    dos.flush();

                    dos.write(bytes);
                    dos.flush();

                    color = readString(dis);
                    Log.w("color done", color);
                    histo_similarity = readString(dis);
                    Log.w("histo done", histo_similarity);
                    //data_len = dis.readInt();
                    //img_result =InputStreamToByteArray(data_len,dis);
                    //img_path = readString(dis);
                    socket.close();
                    //Log.w("done", "img done");
                } catch (Exception e) {
                    Log.w("error", "error occur");
                }
            }
        };
        checkUpdate.start();
        try {
            checkUpdate.join();
        } catch (InterruptedException e) {

        }
    }

    public String readString (DataInputStream dis) throws IOException{
        int length = dis.readInt();
        byte[] data = new byte[length];
        dis.readFully(data, 0, length);
        String text = new String(data, UTF8_CHARSET);
        return text;
    }

    public byte[] InputStreamToByteArray(int data_len, DataInputStream in) {
        int loop = (int)(data_len/1024);
        System.out.println("loop"+Integer.toString(loop));
        byte[] resbytes = new byte[data_len];
        int offset = 0;
        try {
            for (int i=0; i<loop; i++){
                in.readFully(resbytes, offset, 1024);
                offset += 1024;
            }
            in.readFully(resbytes, offset, data_len-(loop*1024));
        } catch (IOException e){
            e.printStackTrace();
        }
        return resbytes;
    }

}