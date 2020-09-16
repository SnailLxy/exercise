package com.lixueyang.exercise.activity.commonintent;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;

import com.lixueyang.exercise.databinding.ActivitySearchIntentBinding;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 搜索相关intent
 * 1、搜索本地音乐
 * 2、在网页上搜索
 */
public class SearchIntentActivity extends AppCompatActivity {

  private ActivitySearchIntentBinding binding;

  public static void startSearchIntentActivity(Activity activity) {
    Intent intent = new Intent(activity, SearchIntentActivity.class);
    activity.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivitySearchIntentBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    initView();
  }

  private void initView() {
    binding.btnSearchMusic.setOnClickListener(view -> {
      Intent intent = new Intent(MediaStore.INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH);
      intent.putExtra(MediaStore.EXTRA_MEDIA_FOCUS, MediaStore.Audio.Artists.ENTRY_CONTENT_TYPE);
      intent.putExtra(MediaStore.EXTRA_MEDIA_ARTIST, binding.etSearchMusic.getText());
      intent.putExtra(SearchManager.QUERY, binding.etSearchMusic.getText());//必填
      if (intent.resolveActivity(getPackageManager()) != null) {
        startActivity(intent);
      }
    });

    //目前只是打开浏览器，并未将文案输入搜索框搜索
    binding.btnSearchWeb.setOnClickListener(view -> {
      Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
      intent.putExtra(SearchManager.QUERY, binding.etSearchMusic.getText());//必填
      if (intent.resolveActivity(getPackageManager()) != null) {
        startActivity(intent);
      }
    });
  }
}