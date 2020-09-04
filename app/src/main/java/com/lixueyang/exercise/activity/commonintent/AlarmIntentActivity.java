package com.lixueyang.exercise.activity.commonintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;

import com.lixueyang.exercise.R;
import com.lixueyang.exercise.databinding.ActivityAlarmIntentBinding;
import com.lixueyang.exercise.utils.IntentUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

/**
 * 闹钟相关常用Intent
 * 必须获取的权限：
 * <uses-permission android:name="com.android.alarm.permission.SET_ALARM" />
 */
public class AlarmIntentActivity extends AppCompatActivity {

  private ActivityAlarmIntentBinding binding;

  public static void startAlarmActivity(Activity activity) {
    Intent intent = new Intent(activity, AlarmIntentActivity.class);
    activity.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_alarm_intent);
    initAlarmView();
  }


  private void initAlarmView() {
    //设置闹钟
    binding.btnSetAlarmIntent.setOnClickListener(view -> {
      Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
          .putExtra(AlarmClock.EXTRA_MESSAGE, getString(R.string.exercise_set_alarm))
          .putExtra(AlarmClock.EXTRA_HOUR, 12)
          .putExtra(AlarmClock.EXTRA_MINUTES, 0);
      if (IntentUtils.isActivityAlive(intent, getPackageManager())) {
        startActivity(intent);
      }
    });
    //设置倒计时
    binding.btnSetTimer.setOnClickListener(view -> {
      Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER)
          .putExtra(AlarmClock.EXTRA_LENGTH, 100)
          .putExtra(AlarmClock.EXTRA_MESSAGE, getString(R.string.exercise_set_timer));
      if (IntentUtils.isActivityAlive(intent, getPackageManager())) {
        startActivity(intent);
      }
    });
    //展示所有闹铃
    binding.btnShowAlarms.setOnClickListener(view -> {
      Intent intent = new Intent(AlarmClock.ACTION_SHOW_ALARMS);
      if (IntentUtils.isActivityAlive(intent, getPackageManager())) {
        startActivity(intent);
      }
    });
  }

}