package com.lixueyang.exercise.activity.commonintent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;

import com.lixueyang.exercise.databinding.ActivitySearchMediaPlayIntentBinding;

public class SearchMediaPlayIntentActivity extends AppCompatActivity {

  private ActivitySearchMediaPlayIntentBinding binding;

  public static void startSearchMediaPlayIntentActivity(Activity activity) {
    Intent intent = new Intent(activity, SearchMediaPlayIntentActivity.class);
    activity.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivitySearchMediaPlayIntentBinding.inflate(getLayoutInflater());
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
  }
}