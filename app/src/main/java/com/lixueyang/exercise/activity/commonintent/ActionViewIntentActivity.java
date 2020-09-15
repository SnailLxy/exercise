package com.lixueyang.exercise.activity.commonintent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.lixueyang.exercise.databinding.ActivityActionViewIntentBinding;

/**
 * 展示相关的intent（即Intent.ACTION_VIEW）
 * 只展示，无法获取对应文件
 */
public class ActionViewIntentActivity extends AppCompatActivity {

  private ActivityActionViewIntentBinding binding;

  public static void startActionViewIntentActivity(Activity activity){
    Intent intent = new Intent(activity, ActionViewIntentActivity.class);
    activity.startActivity(intent);
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityActionViewIntentBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    initView();
  }

  private void initView() {
    binding.btnShowAudio.setOnClickListener(view -> {
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setType("audio/*");
      if (intent.resolveActivity(getPackageManager()) != null) {
        startActivity(intent);
      }
    });
    binding.btnShowPic.setOnClickListener(view -> {
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setType("image/*");
      if (intent.resolveActivity(getPackageManager()) != null) {
        startActivity(intent);
      }
    });
    binding.btnShowVideo.setOnClickListener(view -> {
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setType("video/*");
      if (intent.resolveActivity(getPackageManager()) != null) {
        startActivity(intent);
      }
    });
    binding.btnShowWeb.setOnClickListener(view -> {
      Uri uri = Uri.parse("https://www.baidu.com");
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setData(uri);
      if (intent.resolveActivity(getPackageManager()) != null) {
        startActivity(intent);
      }
    });
  }
}