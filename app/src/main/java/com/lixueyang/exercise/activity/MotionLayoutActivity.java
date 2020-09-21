package com.lixueyang.exercise.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lixueyang.exercise.R;

public class MotionLayoutActivity extends AppCompatActivity {

  public static void startMotionLayoutActivity(Activity activity) {
    Intent intent = new Intent(activity, MotionLayoutActivity.class);
    activity.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_motion_layout);
  }
}