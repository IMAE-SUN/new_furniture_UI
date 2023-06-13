package com.example.newbottomnavi_anti;

import static android.app.Activity.RESULT_OK;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

import static java.util.Arrays.stream;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

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
import java.util.Arrays;
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
    String selectedCbText = "";
    String[][] strarray;
    List<Integer> list;
    int sum = 0;
    int max = 4;
    public String category = new String();
    public String tone = new String();
    public String color = new String();
    public int numOfCat;
    public int[][] hsv = new int[3][3];
    public String histo_similarity = new String();
    private static final int MY_PERMISSION_CAMERA = 1111;
    private static final int REQUEST_TAKE_PHOTO = 2222;
    private static final int REQUEST_TAKE_ALBUM = 3333;
    private static final int REQUEST_IMAGE_CROP = 4444;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    String mCurrentPhotoPath;

    Uri imageUri;
    Uri photoURI, albumURI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommendation, container, false);

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
                switch (checkedId) {
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
                switch (checkedId) {
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
        CheckBox cb3 = view.findViewById(R.id.cb_question_2_closet);
        CheckBox cb4 = view.findViewById(R.id.cb_question_2_curtain);
        CheckBox cb5 = view.findViewById(R.id.cb_question_2_desk);
        CheckBox cb6 = view.findViewById(R.id.cb_question_2_lamp);
        CheckBox cb7 = view.findViewById(R.id.cb_question_2_shelf);
        CheckBox cb8 = view.findViewById(R.id.cb_question_2_sofa);
        CheckBox cb9 = view.findViewById(R.id.cb_question_2_table);


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
        recommend_layout = view.findViewById(R.id.linearLayout_whatsyourstyle);

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
                if (more_opt_layout.getVisibility() == View.GONE) {
                    more_opt_layout.setVisibility(View.VISIBLE);
                    more_opt.setText("접기");
                } else {
                    more_opt_layout.setVisibility(View.GONE);
                    more_opt.setText("더보기");
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
                //권한 체크
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

//        갤러리에서 가져오기
        gallery_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("gallery", "gallery 클릭함");
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, REQUEST_IMAGE_PICK);
            }
        });

//        checkPermission();

//        추천하기 (파이썬과 연결)
        recommend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("recommedation", "recommendation 시작함");
//                if(recommend_layout.getVisibility() == View.GONE){
//                    recommend_layout.setVisibility(View.VISIBLE);
//                }
                //추천하기 누르면 카메라, 갤러리 버튼 없어짐
                recommend_layout.setVisibility(View.GONE);

                Toast.makeText(getContext(), RadioText, Toast.LENGTH_SHORT).show();
                Log.e("Final Radio Button", RadioText);


                if (cb1.isChecked()) {
                    selectedCbText += "1 ";
                }
                if (cb2.isChecked()) {
                    selectedCbText += "2 ";
                }
                if (cb3.isChecked()) {
                    selectedCbText += "3 ";
                }
                if (cb4.isChecked()) {
                    selectedCbText += "4 ";
                }
                if (cb5.isChecked()) {
                    selectedCbText += "5 ";
                }
                if (cb6.isChecked()) {
                    selectedCbText += "6 ";
                }
                if (cb7.isChecked()) {
                    selectedCbText += "7 ";
                }
                if (cb8.isChecked()) {
                    selectedCbText += "8 ";
                }
                if (cb9.isChecked()) {
                    selectedCbText += "9 ";
                }

                Toast.makeText(getContext(), selectedCbText, Toast.LENGTH_SHORT).show();
                Log.e("Final CheckBox", selectedCbText);

                if (selectedCbText == null) {
                    Toast.makeText(getContext(), "모든 옵션을 선택해주세요", Toast.LENGTH_SHORT).show();
                } else {
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            displayImage(imageBitmap);
        } else if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                displayImage(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayImage(Bitmap imageBitmap) {
        img_wys_1.setImageBitmap(imageBitmap);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            } else {
                Toast.makeText(getActivity(), "카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
        }
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
//
//
//    private void getAlbum(){
//        Log.i("getAlbum", "Call");
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
//        startActivityForResult(intent, 1);
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case 1:
//                if (resultCode == RESULT_OK) {
//                    Uri uri = data.getData();
//                    img_wys_1.setImageURI(uri);
//                }
//                break;
//        }
//    }


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
        firebaseAuth = firebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users");

//                분위기 전달
        reference.child(uid).child("Preference").child("Mood").setValue(RadioText);

//                가구 전달
        if (selectedCbText != null) {
            String[] temp = selectedCbText.split(" ");
            int n = temp.length;
            for (int i = 0; i < n; i++) {
                Log.e("CheckBox", temp[i]);
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("furniture", temp[i]);
                reference.child(uid).child("Preference").child("Furniture").push().setValue(hashMap);
            }
        }


//                퍼센트 전달
        reference.child(uid).child("Preference").child("seekbar").setValue(RadioText2);


        //yolo 사용하는 서버와 연결
        mHandler = new Handler();
        BitmapDrawable drawable = (BitmapDrawable) img_wys_1.getDrawable();
        img = drawable.getBitmap();
        Log.w("connect", "연결 하는중");
        Thread checkUpdate = new Thread() {
            public void run() {
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                byte[] bytes = byteArray.toByteArray();
                int data_len = 0;
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

                    category = "0 1 4";
                    numOfCat = 3;
                    dos.writeUTF(category);
                    dos.flush();

                    color = readString(dis);
                    tone = readString(dis);
                    Log.w("color,tone done", color);

                    int[][] recByCat = new int[numOfCat][10];
                    for (int i = 0; i < numOfCat; i++) {
                        String[] strTmp = new String[10];
                        strTmp = readString(dis).split(" ");
                        for (int j = 0; j < 10; j++) {
                            recByCat[i][j] = Integer.parseInt(strTmp[j]);
                        }
                    }

                    //histo_similarity = readString(dis);
                    //Log.w("histo done", histo_similarity);

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

    public String readString(DataInputStream dis) throws IOException {
        int length = dis.readInt();
        byte[] data = new byte[length];
        dis.readFully(data, 0, length);
        String text = new String(data, UTF8_CHARSET);
        return text;
    }

    public byte[] InputStreamToByteArray(int data_len, DataInputStream in) {
        int loop = (int) (data_len / 1024);
        System.out.println("loop" + Integer.toString(loop));
        byte[] resbytes = new byte[data_len];
        int offset = 0;
        try {
            for (int i = 0; i < loop; i++) {
                in.readFully(resbytes, offset, 1024);
                offset += 1024;
            }
            in.readFully(resbytes, offset, data_len - (loop * 1024));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resbytes;
    }

//    private void galleryAddPic(){
//        Log.i("galleryAddPic", "Call");
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        // 해당 경로에 있는 파일을 객체화(새로 파일을 만든다는 것으로 이해하면 안 됨)
//        File f = new File(mCurrentPhotoPath);
//        Uri contentUri = Uri.fromFile(f);
//        mediaScanIntent.setData(contentUri);
//        getContext().sendBroadcast(mediaScanIntent);
//        Toast.makeText(getContext(), "사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show();
//    }
//
//     //카메라 전용 크랍
//    public void cropImage(){
//        Log.i("cropImage", "Call");
//        Log.i("cropImage", "photoURI : " + photoURI + " / albumURI : " + albumURI);
//
//        Intent cropIntent = new Intent("com.android.camera.action.CROP");
//
//        // 50x50픽셀미만은 편집할 수 없다는 문구 처리 + 갤러리, 포토 둘다 호환하는 방법
//        cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//        cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        cropIntent.setDataAndType(photoURI, "image/*");
//        //cropIntent.putExtra("outputX", 200); // crop한 이미지의 x축 크기, 결과물의 크기
//        //cropIntent.putExtra("outputY", 200); // crop한 이미지의 y축 크기
//        cropIntent.putExtra("aspectX", 1); // crop 박스의 x축 비율, 1&1이면 정사각형
//        cropIntent.putExtra("aspectY", 1); // crop 박스의 y축 비율
//        cropIntent.putExtra("scale", true);
//        cropIntent.putExtra("output", albumURI); // 크랍된 이미지를 해당 경로에 저장
//        startActivityForResult(cropIntent, REQUEST_IMAGE_CROP);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case REQUEST_TAKE_PHOTO:
//                if (resultCode == Activity.RESULT_OK) {
//                    try {
//                        Log.i("REQUEST_TAKE_PHOTO", "OK");
//                        galleryAddPic();
//
//                        img_wys_1.setImageURI(imageUri);
//                    } catch (Exception e) {
//                        Log.e("REQUEST_TAKE_PHOTO", e.toString());
//                    }
//                } else {
//                    Toast.makeText(getContext(), "사진찍기를 취소하였습니다.", Toast.LENGTH_SHORT).show();
//                }
//                break;
//
//            case REQUEST_TAKE_ALBUM:
//                if (resultCode == Activity.RESULT_OK) {
//
//                    if(data.getData() != null){
//                        try {
//                            File albumFile = null;
//                            albumFile = createImageFile();
//                            photoURI = data.getData();
//                            albumURI = Uri.fromFile(albumFile);
//                            cropImage();
//                        }catch (Exception e){
//                            Log.e("TAKE_ALBUM_SINGLE ERROR", e.toString());
//                        }
//                    }
//                }
//                break;
//
//            case REQUEST_IMAGE_CROP:
//                if (resultCode == Activity.RESULT_OK) {
//
//                    galleryAddPic();
//                    img_wys_1.setImageURI(albumURI);
//                }
//                break;
//        }
//    }
//
//    private void checkPermission(){
//        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            // 처음 호출시엔 if()안의 부분은 false로 리턴 됨 -> else{..}의 요청으로 넘어감
//            if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) ||
//                    (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA))) {
//                new AlertDialog.Builder(getContext())
//                        .setTitle("알림")
//                        .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다.")
//                        .setNeutralButton("설정", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
//                                startActivity(intent);
//                            }
//                        })
//                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                getActivity().finish();
//                            }
//                        })
//                        .setCancelable(false)
//                        .create()
//                        .show();
//            } else {
//                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, MY_PERMISSION_CAMERA);
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSION_CAMERA:
//                for (int i = 0; i < grantResults.length; i++) {
//                    // grantResults[] : 허용된 권한은 0, 거부한 권한은 -1
//                    if (grantResults[i] < 0) {
//                        Toast.makeText(getContext(), "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                }
//                // 허용했다면 이 부분에서..
//
//                break;
//        }
//    }

}