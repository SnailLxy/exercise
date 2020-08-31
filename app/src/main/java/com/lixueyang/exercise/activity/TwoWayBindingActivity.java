package com.lixueyang.exercise.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lixueyang.exercise.R;
import com.lixueyang.exercise.databinding.ActivityTwoWayBindingBinding;
import com.lixueyang.exercise.models.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

/**
 * 测试：DataBiding 双向绑定
 */
public class TwoWayBindingActivity extends AppCompatActivity {

  private ActivityTwoWayBindingBinding binding;
  private User user;

  public static void startTwoWayBindingActivity(Activity activity) {
    Intent intent = new Intent(activity, TwoWayBindingActivity.class);
    activity.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_two_way_binding);
    initData();
  }

  private void initData() {
    user = new User("xue", "yang");
    binding.setUser(user);
  }
}