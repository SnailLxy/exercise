package com.lixueyang.exercise.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;

import com.lixueyang.exercise.R;
import com.lixueyang.exercise.databinding.ActivityStorageAccessFrameworkBinding;
import com.lixueyang.exercise.utils.CollectionUtils;

import java.util.List;

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
 * <p>
 * <p>
 * 1、Intent.ACTION_OPEN_DOCUMENT获取的uri可以长期保持。
 * Intent.ACTION_GET_CONTENT获取的uri在app关闭后后会失效，所以这里的uri保存到本地会出现无效情况。
 */
public class StorageAccessFrameworkActivity extends AppCompatActivity {
  public static final String TAG = "MainActivity";
  private static final int REQUEST_CODE_FOR_SINGLE_FILE = 100;
  private static final int REQUEST_CODE_GET_CONTACTS = 101;
  private static final int REQUEST_CODE_GET_PIC = 102;
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
    binding.setLifecycleOwner(this);
    binding.btnOpenFile.setOnClickListener(view -> goSelectFile());
    binding.btnOpenPicFromApp.setOnClickListener(view -> openPicFromApp());
    binding.btnOpenPicFromProvider.setOnClickListener(view -> openPicFromContentProvide());
    binding.btnOpenMusic.setOnClickListener(view -> openMusic());
    binding.btnOpenVideo.setOnClickListener(view -> openVideo());
    binding.btnOpenContact.setOnClickListener(view -> goSelectedContact());
    binding.btnOpenWeb.setOnClickListener(view -> openWeb());
  }

  /**
   * 不用存储权限，获取图片
   */
  private void goSelectFile() {
    //通过系统的文件浏览器选择一个文件
    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
    //筛选，只显示可以“打开”的结果，如文件(而不是联系人或时区列表)
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    //过滤只显示图像类型文件
    intent.setType("image/*");
    if (isActivityAlive(intent)) {
      startActivityForResult(intent, REQUEST_CODE_FOR_SINGLE_FILE);
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
      startActivityForResult(imageIntent, REQUEST_CODE_GET_PIC);
    }
  }

  /**
   * 通过文档Provide获取图片
   */
  public void openPicFromDocumentProvide() {
    Intent imageIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
    imageIntent.setType("image/*");
    imageIntent.addCategory(Intent.CATEGORY_OPENABLE);
    if (isActivityAlive(imageIntent)) {
      startActivityForResult(imageIntent, REQUEST_CODE_GET_PIC);
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
      startActivityForResult(imageIntent, REQUEST_CODE_GET_PIC);
    }
  }

  /**
   * 通过view获取图片
   * 这里不能使用imageIntent.addCategory(Intent.CATEGORY_OPENABLE);
   */
  public void openPicFromView() {
    Intent imageIntent = new Intent(Intent.ACTION_VIEW);
    imageIntent.setType("image/*");
    if (isActivityAlive(imageIntent)) {
      startActivityForResult(imageIntent, REQUEST_CODE_GET_PIC);
    }
  }

  public void openWeb() {
    Uri webPage = Uri.parse("http://www.android.com");
    Intent webIntent = new Intent(Intent.ACTION_VIEW, webPage);
    // Create intent to show chooser
    Intent chooser = Intent.createChooser(webIntent, "www.android.com");

    if (isActivityAlive(chooser)) {
      startActivity(chooser);
    }
  }

  public void openMusic() {
    Intent audioIntent = new Intent(Intent.ACTION_GET_CONTENT);
    audioIntent.setType("audio/*");
    if (isActivityAlive(audioIntent)) {
      startActivityForResult(audioIntent, 3);
    }
  }

  public void openVideo() {
    Intent videoIntent = new Intent(Intent.ACTION_GET_CONTENT);
    videoIntent.setType("video/*");

    if (isActivityAlive(videoIntent)) {
      startActivityForResult(videoIntent, 4);
    }
  }

  /**
   * 不用存储权限，获取联系人
   * 打开手机上的联系人app
   */
  private void goSelectedContact() {
    Intent intent = new Intent(Intent.ACTION_PICK);
    intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
    if (isActivityAlive(intent)) {
      startActivityForResult(intent, REQUEST_CODE_GET_CONTACTS);
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
    Uri uri = resultData.getData();
    if (uri == null) {
      return;
    }
    Cursor cursor = null;
    switch (requestCode) {
      case REQUEST_CODE_FOR_SINGLE_FILE:
        // 获取图片信息
        cursor = this.getContentResolver()
            .query(uri, IMAGE_PROJECTION, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
          String displayName = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
          String size = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
          Log.i(TAG, "OPEN_DOCUMENT_Uri: " + uri.toString());
          Log.i(TAG, "OPEN_DOCUMENT_Name: " + displayName);
          Log.i(TAG, "OPEN_DOCUMENT_Size: " + size);
        }
        break;
      case REQUEST_CODE_GET_CONTACTS:
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
        cursor = getContentResolver().query(uri, null,
            null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
          int numberIndex = cursor.getColumnIndex(projection[0]);
          int nameIndex = cursor.getColumnIndex(projection[1]);

        }
        break;
      case REQUEST_CODE_GET_PIC:
        // 获取图片信息
        cursor = this.getContentResolver()
            .query(uri, IMAGE_PROJECTION, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
          String displayName = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
          String size = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
          Log.i(TAG, "GET_PIC_Uri: " + uri.toString());
          Log.i(TAG, "GET_PIC_Name: " + displayName);
          Log.i(TAG, "GET_PIC_Size: " + size);
        }
        break;
    }
    if (cursor != null) {
      cursor.close();
    }
  }
}
