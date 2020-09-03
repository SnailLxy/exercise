package com.lixueyang.exercise.activity;

import android.os.Bundle;

import com.lixueyang.exercise.activity.commonintent.CommonIntentActivity;
import com.lixueyang.exercise.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
  public static final String TAG = "MainActivity";
  private ActivityMainBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    initListener();

  }

  private void initListener() {
    binding.btnGotoSafActivity.setOnClickListener(view -> StorageAccessFrameworkActivity.startSAFActivity(MainActivity.this));
    binding.btnGotoTwoWayBindingActivity.setOnClickListener(view -> TwoWayBindingActivity.startTwoWayBindingActivity(MainActivity.this));
    binding.btnGotoRecentScreenActivity.setOnClickListener(view -> RecentScreenActivity.startRecentScreenActivity(MainActivity.this));
    binding.btnGotoCommonIntentActivity.setOnClickListener(view -> CommonIntentActivity.startCommonIntentActivity(MainActivity.this));
  }


}