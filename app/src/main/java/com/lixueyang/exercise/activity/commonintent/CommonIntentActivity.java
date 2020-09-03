package com.lixueyang.exercise.activity.commonintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;

import com.lixueyang.exercise.R;
import com.lixueyang.exercise.databinding.ActivityCommonIntentBinding;
import com.lixueyang.exercise.utils.IntentUtils;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

/**
 * 常用Intent练习
 * https://developer.android.google.cn/guide/components/intents-common#CallCar
 */
public class CommonIntentActivity extends AppCompatActivity {

  private ActivityCommonIntentBinding binding;

  public static void startCommonIntentActivity(Activity activity) {
    Intent intent = new Intent(activity, CommonIntentActivity.class);
    activity.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_common_intent);
    initListener();
    initCalendarView();
  }

  private void initListener() {
    binding.btnEasiestIntent.setOnClickListener(view -> {
      //隐式intent最低标准是调用端只有一个action，
      // 接受端activity的intent过滤器包含对应的action及一个android.intent.category.DEFAULT
      Intent intent = new Intent();
      intent.setAction("lxy.intent.action.CALL");
      if (IntentUtils.isActivityAlive(intent, getPackageManager())) {
        startActivity(intent);
      }
    });
    binding.btnGotoAlarmActivity.setOnClickListener(view -> AlarmActivity.startAlarmActivity(this));
    binding.btnGotoCameraActivity.setOnClickListener(view -> CameraIntentActivity.startCameraIntentActivity(this));
  }

  /**
   * 日历相关
   */
  private void initCalendarView() {
    binding.btnAddCalendar.setOnClickListener(view -> {
      Intent intent = new Intent(Intent.ACTION_INSERT)
          .setData(CalendarContract.Events.CONTENT_URI)
          .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
          .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, System.currentTimeMillis() + TimeUnit.DAYS.toMillis(10))
          .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, System.currentTimeMillis() + TimeUnit.DAYS.toMillis(11))
          .putExtra(CalendarContract.Events.TITLE, getString(R.string.exercise_add_calendar))
          .putExtra(CalendarContract.Events.EVENT_LOCATION, "北京");
      if (IntentUtils.isActivityAlive(intent, getPackageManager())) {
        startActivity(intent);
      }
    });
  }

}