package com.example.newbottomnavi_anti;

import static android.app.Activity.RESULT_OK;

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

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import java.util.List;

public class Recommendation extends Fragment {

    public Recommendation() {
        // Required empty public constructor
    }

    ImageView img_wys_1, img_wys_2, img_wys_3, img_wys_4, img_recom_a, img_recom_a_1, img_recom_b, img_recom_b_1, img_recom_c, img_recom_c_1, img_recom_d, img_recom_d_1;
    Button recommend_btn;
    ImageButton refresh_btn;
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
        View view = inflater.inflate(R.layout.fragment_recommendation, container, false);

        Log.e("추천", "추천들어옴");

//        카메라, 갤러리 버튼
//        TODO : 연결하기
        Button camera_btn = view.findViewById(R.id.camera_recom);
        Button gallery_btn = view.findViewById(R.id.gallery_recom);

//        그렇게 불러온 사진들 띄워주기 (최대 4개)
//        TODO : 클릭 시 없어져야 함
        img_wys_1 = view.findViewById(R.id.img_whatsyourstle_1);
        img_wys_2 = view.findViewById(R.id.img_whatsyourstle_2);
        img_wys_3 = view.findViewById(R.id.img_whatsyourstle_3);
        img_wys_4 = view.findViewById(R.id.img_whatsyourstle_4);

//        추천하기 버튼
//        TODO : 클릭 시 python 코드와 연결되도록
        recommend_btn = view.findViewById(R.id.recommend_btn);

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
                connect();
            }
        });

//        추천 새로고침
        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("refresh", "refresh 클릭함");
                Toast.makeText(getContext(), "refresh", Toast.LENGTH_SHORT).show();
            }
        });

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

    public void connect() {
        mHandler = new Handler();
        BitmapDrawable drawable = (BitmapDrawable) img_wys_1.getDrawable();
        img = drawable.getBitmap();
        Log.w("connect", "연결 하는중");
        Thread checkUpdate = new Thread() {
            public void run() {
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                byte[] bytes = byteArray.toByteArray();
                String result = new String();
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

                    result = readString(dis);
                    Log.w("done", result);
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
//
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

    // 카메라 전용 크랍
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