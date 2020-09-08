package com.lixueyang.exercise.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.lixueyang.exercise.R;
import com.lixueyang.exercise.databinding.ActivityStorageAccessFrameworkBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

/**
 * Created on 2020/8/21.
 * <p>
 * 测试：SAF系统
 * 1、Intent.ACTION_GET_CONTENT打开的是一个内容提供器
 * 返回的URI为：content://com.android.providers.media.documents/document/image%3A8645
 * 2、Intent.ACTION_OPEN_DOCUMENT和Intent.ACTION_GET_CONTENT相同，都是打开一个内容提供器
 * 返回的UIRI为：content://com.android.providers.media.documents/document/image%3A8645
 * 2、Intent.ACTION_PICK打开的是一个对应app界面，如果有多个app的话，会有一个弹框展示所有的app…..
 * 返回的URI为：“content://com.miui.gallery.open/raw//storage/emulated/0/DCIM/Camera/IMG_20200826_111744.jpg”，
 * <p>
 * <p>
 * 1、Intent.ACTION_GET_CONTENT与Intent.ACTION_OPEN_DOCUMENT最好加上Intent.CATEGORY_OPENABLE
 * 而Intent.ACTION_PICK则不可以
 * Intent.CATEGORY_OPENABLE可以保证文件可以从contentProvider获取，以及通过 openFileDescriptor() 以文件流形式获取文件
 * <p>
 * <p>
 * 1、Intent.ACTION_OPEN_DOCUMENT获取的uri可以长期保持。可以保存到SharedPreferences中，
 * Intent.ACTION_GET_CONTENT和Intent.ACTION_PICK获取的uri在activity关闭后后会失效，所以这里的uri保存到本地会出现无效情况。
 * <p>
 * <p>
 * 1、Intent.ACTION_VIEW只是展示图片，而不能获取图片
 */
public class StorageAccessFrameworkActivity extends AppCompatActivity {
  public static final String TAG = "StorageAccessFramework";

  public static final String SP_PIC_URI_KEY = "uri";
  private static final int REQUEST_CODE_GET_PIC_FROM_DOCUMENT = 100;
  private static final int REQUEST_CODE_GET_PIC_FROM_CONTENT = 101;
  private static final int REQUEST_CODE_GET_PIC_FROM_APP = 102;
  private static final int REQUEST_CODE_GET_PIC_FROM_VIEW = 103;
  private static final int REQUEST_CODE_GET_MULTIPLE_PIC_FROM_CONTENT = 104;

  private final String[] IMAGE_PROJECTION = {
      MediaStore.Images.Media.DISPLAY_NAME,
      MediaStore.Images.Media.SIZE,
      MediaStore.Images.Media._ID};

  private ActivityStorageAccessFrameworkBinding binding;

  public static void startSAFActivity(Activity activity) {
    Intent intent = new Intent(activity, StorageAccessFrameworkActivity.class);
    activity.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_storage_access_framework);
    binding.btnOpenPicFromDocument.setOnClickListener(view -> openPicFromDocumentProvide());
    binding.btnOpenPicFromApp.setOnClickListener(view -> openPicFromApp());
    binding.btnOpenPicFromProvider.setOnClickListener(view -> openPicFromContentProvide());
    binding.btnOpenPicFromView.setOnClickListener(view -> openPicFromView());
    binding.btnOpenMultiplePicFromProvider.setOnClickListener(view -> openMultiplePicFromContentProvide());
    String picUri = getPreferences(Context.MODE_PRIVATE).getString(SP_PIC_URI_KEY, "");
    if (!TextUtils.isEmpty(picUri)) {
      binding.ivShowPic.setImageURI(Uri.parse(picUri).buildUpon().build());
    }
  }

  /**
   * 通过文档Provide获取图片
   */
  public void openPicFromDocumentProvide() {
    //通过系统的文件浏览器选择一个文件
    Intent imageIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
    imageIntent.setType("image/*");//"audio/*"对应音频，"video/*"对应视频
    imageIntent.addCategory(Intent.CATEGORY_OPENABLE);
    if (isActivityAlive(imageIntent)) {
      startActivityForResult(imageIntent, REQUEST_CODE_GET_PIC_FROM_DOCUMENT);
    }
  }

  /**
   * 通过内容Provide获取图片
   */
  public void openPicFromContentProvide() {
    Intent imageIntent = new Intent(Intent.ACTION_GET_CONTENT);
    imageIntent.addCategory(Intent.CATEGORY_OPENABLE);
    imageIntent.setType("image/*");
    if (isActivityAlive(imageIntent)) {
      startActivityForResult(imageIntent, REQUEST_CODE_GET_PIC_FROM_CONTENT);
    }
  }

  /**
   * 通过其他app获取图片
   * 这里不能使用imageIntent.addCategory(Intent.CATEGORY_OPENABLE);
   */
  public void openPicFromApp() {
    Intent imageIntent = new Intent(Intent.ACTION_PICK);
    imageIntent.setType("image/*");
    if (isActivityAlive(imageIntent)) {
      startActivityForResult(imageIntent, REQUEST_CODE_GET_PIC_FROM_APP);
    }
  }

  /**
   * 通过view获展示图片，无法获取图片
   * 这里不能使用imageIntent.addCategory(Intent.CATEGORY_OPENABLE);
   */
  public void openPicFromView() {
    Intent imageIntent = new Intent(Intent.ACTION_VIEW);
    imageIntent.setType("image/*");
    if (isActivityAlive(imageIntent)) {
      startActivityForResult(imageIntent, REQUEST_CODE_GET_PIC_FROM_VIEW);
    }
  }

  /**
   * 通过内容Provide获取图片
   */
  public void openMultiplePicFromContentProvide() {
    Intent imageIntent = new Intent(Intent.ACTION_GET_CONTENT);
    imageIntent.addCategory(Intent.CATEGORY_OPENABLE);
    imageIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
    imageIntent.setType("image/*");
    if (isActivityAlive(imageIntent)) {
      startActivityForResult(imageIntent, REQUEST_CODE_GET_MULTIPLE_PIC_FROM_CONTENT);
    }
  }

  private boolean isActivityAlive(Intent intent) {
    return intent.resolveActivity(getPackageManager()) != null;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent resultData) {
    super.onActivityResult(requestCode, resultCode, resultData);
    if (resultCode != Activity.RESULT_OK) {
      return;
    }
    if (resultData == null) {
      return;
    }
    // 获取选择文件Uri
    Uri uri;
    switch (requestCode) {
      case REQUEST_CODE_GET_PIC_FROM_DOCUMENT:
      case REQUEST_CODE_GET_PIC_FROM_CONTENT:
      case REQUEST_CODE_GET_PIC_FROM_APP:
      case REQUEST_CODE_GET_PIC_FROM_VIEW:
        if (resultData.getData() == null) {
          return;
        }
        uri = resultData.getData();
        // 获取图片信息
        Cursor cursor = this.getContentResolver()
            .query(uri, IMAGE_PROJECTION, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
          String displayName = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
          String size = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
          Log.e(TAG, " Uri: " + uri.toString() + "Name:" + displayName + "size:" + size);
          cursor.close();
        }
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SP_PIC_URI_KEY, uri.toString());
        editor.apply();
        binding.ivShowPic.setImageURI(uri);
        break;
      case REQUEST_CODE_GET_MULTIPLE_PIC_FROM_CONTENT:
        if (resultData.getData() != null) {
          //只获取单张图片
          Log.e(TAG, "REQUEST_CODE_GET_MULTIPLE_PIC_FROM_CONTENT" + " Uri: " + resultData.getData().toString());
        } else if (resultData.getClipData() != null) {
          //长按选中多张图片
          ClipData clipData = resultData.getClipData();
          for (int i = 0; i < clipData.getItemCount(); i++) {
            ClipData.Item item = clipData.getItemAt(i);
            Log.e(TAG, "REQUEST_CODE_GET_MULTIPLE_PIC_FROM_CONTENT" + " Uri: " + item.getUri().toString() + ",text:" + item.getText() + ",position:" + i);
          }
        }
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + requestCode);
    }
  }
}
