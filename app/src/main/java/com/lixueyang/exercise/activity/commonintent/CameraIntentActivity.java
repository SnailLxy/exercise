package com.lixueyang.exercise.activity.commonintent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;

import com.lixueyang.exercise.R;
import com.lixueyang.exercise.databinding.ActivityCameraIntentBinding;
import com.lixueyang.exercise.utils.ImageUtils;
import com.lixueyang.exercise.utils.IntentUtils;
import com.lixueyang.exercise.utils.LxyFileUtils;

import java.io.File;

public class CameraIntentActivity extends AppCompatActivity {

  private static final int REQUEST_CODE_CAPTURE_IMAGE = 101;
  private static final int REQUEST_CODE_CAPTURE_VIDEO = 102;
  private ActivityCameraIntentBinding binding;
  private File imageFile;
  private String imageFilePath;
  private Uri imageFileUri;

  public static void startCameraIntentActivity(Activity activity) {
    Intent intent = new Intent(activity, CameraIntentActivity.class);
    activity.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_camera_intent);
    initView();
    initData();
  }

  private void initData() {
    imageFile = LxyFileUtils.createFile(this);
    if (imageFile != null) {
      imageFilePath = imageFile.getAbsolutePath();
    }
  }

  private void initView() {
    binding.btnCaptureImage.setOnClickListener(view -> {
      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

      if (IntentUtils.isActivityAlive(intent, getPackageManager())) {
        if (imageFile != null) {
          //前面为包名，后面为fileprovider固定值，使用包名便于区分
          String authority = "com.lixueyang.exercise.fileProvider";
          imageFileUri = FileProvider.getUriForFile(CameraIntentActivity.this, authority, imageFile);
          intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);//指定uri以后，返回的data为null
          intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
          startActivityForResult(intent, REQUEST_CODE_CAPTURE_IMAGE);
        }
      }
    });
    binding.btnCaptureVideo.setOnClickListener(view -> {
      Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
      if (IntentUtils.isActivityAlive(intent, getPackageManager())) {
        startActivityForResult(intent, REQUEST_CODE_CAPTURE_VIDEO);
      }
    });
    /**
     * 并不会返回图片
     */
    binding.btnCaptureImageStill.setOnClickListener(view -> {
      Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
      if (IntentUtils.isActivityAlive(intent, getPackageManager())) {
        startActivityForResult(intent, REQUEST_CODE_CAPTURE_IMAGE);
      }
    });
    /**
     * 并不会返回视频
     */
    binding.btnCaptureVideoMode.setOnClickListener(view -> {
      Intent intent = new Intent(MediaStore.INTENT_ACTION_VIDEO_CAMERA);
      if (IntentUtils.isActivityAlive(intent, getPackageManager())) {
        startActivityForResult(intent, REQUEST_CODE_CAPTURE_VIDEO);
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode != Activity.RESULT_OK) {
      return;
    }
    switch (requestCode) {
      case REQUEST_CODE_CAPTURE_IMAGE:
        if (imageFileUri != null) {
          //展示压缩尺寸图片
//          Bitmap bitmap = ImageUtils.compressBitmap(imageFilePath, binding.ivShowCaptureImage.getWidth(), binding.ivShowCaptureImage.getHeight());
//          binding.ivShowCaptureImage.setImageBitmap(bitmap);
          //展示完整尺寸图片
          binding.ivShowCaptureImage.setImageURI(imageFileUri);
          galleryAddPic();
        }

        break;
      case REQUEST_CODE_CAPTURE_VIDEO:
        if (data != null) {
          binding.vvShowCaptureVideo.setVideoURI(data.getData());
          binding.vvShowCaptureVideo.start();
        }
        break;
    }
  }

  /**
   * 将存在getExternalFilesDir目录下的图片，添加到相册中
   */
  private void galleryAddPic() {
    if (imageFileUri == null) {
      return;
    }
    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
    mediaScanIntent.setData(imageFileUri);
    sendBroadcast(mediaScanIntent);
  }

}