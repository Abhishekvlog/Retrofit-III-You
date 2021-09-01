package com.example.retrofit_iii_you;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private VideoView mVideoView;
    private Button mBtnUpload;
    private Button mBtnOpenGallery;
    private String VideoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mVideoView = findViewById(R.id.VideoView);
        mBtnUpload = findViewById(R.id.btnUpload);
        mBtnOpenGallery = findViewById(R.id.btnGallery);

        mBtnOpenGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPermissionGranted()){
                    OpenGallery();

                }
                else {
                    requestPermission();
                }
            }
        });
        mBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiService apiService =Network.getInstance().create(ApiService.class);
                File file = new File(VideoPath);
                RequestBody requestBody =RequestBody.create(MediaType.parse("video/*"),file);
                MultipartBody.Part part =MultipartBody.Part.createFormData("video",file.getName(),requestBody);
                apiService.uploadVideo(part).enqueue(new Callback<ResponseDTO>() {
                    @Override
                    public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseDTO> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void requestPermission() {
        String[] permission =new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(MainActivity.this,permission,101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
            OpenGallery();;
        }
        else {
            Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void OpenGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        launchGallery.launch(intent);
    }

    private boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == 0;
    }
    private ActivityResultLauncher<Intent> launchGallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getData() != null) {
                        Uri SelectedUri = result.getData().getData();
                        mVideoView.setVideoURI(SelectedUri);
                        mVideoView.requestFocus();
                        mVideoView.start();
                        getVideoPathFromUri(SelectedUri);
                    }
                }
            }
    );
    private Cursor getVideoPathFromUri(Uri SelectedUri ){
        String[] filePath = {MediaStore.Video.Media.DATA};
        Cursor c = getContentResolver().query(SelectedUri, filePath,
                null, null, null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePath[0]);
        VideoPath = c.getString(columnIndex);
        return c;
    }
}