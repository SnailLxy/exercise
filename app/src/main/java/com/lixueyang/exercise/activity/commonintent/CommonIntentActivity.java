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
 * 常见的intent：
 *        1、Intent.ACTION_INSERT  插入数据，目前练习了插入日历、联系人
 *        2、Intent.ACTION_PICK    通过打开手机上的相应app获取数据，目前练习了打开图片，打开联系人
 *        3、Intent.ACTION_VIEW    通过其他app展示数据，目前练习了展示图片、音频、视频、网站、联系人、地图
 *        4、AlarmClock.ACTION_***_***    闹钟相关的action，都在这个类里，必须请求SET_ALARM权限
 *        5、MediaStore.ACTION_***_***    媒体相关的action。都在这个类里，包括图片，视频，音频
 *        6、Intent.ACTION_SEND    发送信息相关的action，目前练习了发送邮件、短信
 *        6、Settings.ACTION_***_SETTINGS    设置相关的action，都在这个类中
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
    binding.btnGotoAlarmActivity.setOnClickListener(view -> AlarmIntentActivity.startAlarmActivity(this));
    binding.btnGotoCameraActivity.setOnClickListener(view -> CameraIntentActivity.startCameraIntentActivity(this));
    binding.btnGotoContactsActivity.setOnClickListener(view -> ContactsIntentActivity.startContactsIntentActivity(this));
    binding.btnGotoMapActivity.setOnClickListener(view -> MapIntentActivity.startMapIntentActivity(this));
    binding.btnGotoActionViewIntentActivity.setOnClickListener(view -> ActionViewIntentActivity.startActionViewIntentActivity(this));
    binding.btnGotoSearchMediaPlayIntentActivity.setOnClickListener(view -> SearchIntentActivity.startSearchIntentActivity(this));
    binding.btnGotoNewNoteIntentActivity.setOnClickListener(view -> NewNoteIntentActivity.startNewNoteIntentActivity(this));
    binding.btnGotoCallIntentActivity.setOnClickListener(view -> CallIntentActivity.startCallIntentActivity(this));
    binding.btnGotoSendIntentActivity.setOnClickListener(view -> SendIntentActivity.startSendIntentActivity(this));
    binding.btnGotoSettingIntentActivity.setOnClickListener(view -> SettingIntentActivity.startSettingIntentActivity(this));
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