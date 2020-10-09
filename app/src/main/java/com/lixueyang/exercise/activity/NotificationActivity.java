package com.lixueyang.exercise.activity;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.lixueyang.exercise.R;
import com.lixueyang.exercise.activity.commonintent.CommonIntentActivity;
import com.lixueyang.exercise.databinding.ActivityNotificationBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class NotificationActivity extends AppCompatActivity {

  private static final String CHANNEL_NORMAL_ID = "简单通知";
  private static final int NORMAL_NOTIFICATION_ID = 1;
  private static final String NORMAL_NOTIFICATION_TEXT = "这是一条测试普通通知的通知，本身并没有什么信息，这里为了看看如果文案过长的话，界面显示的长度。感觉到这个长度应该就可以了！！！！";

  private ActivityNotificationBinding binding;
  private NotificationManager manager;
  private NotificationCompat.Builder normalNotificationBuilder;

  public static void startNotificationActivity(Activity activity) {
    Intent intent = new Intent(activity, NotificationActivity.class);
    activity.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityNotificationBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    initView();
    createNotification();
    createNotificationChannel();
  }

  private void initView() {
    binding.cbShowBigText.setOnCheckedChangeListener((buttonView, isChecked) -> {
      if (isChecked) {
        normalNotificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(NORMAL_NOTIFICATION_TEXT));
      } else {
        normalNotificationBuilder.setStyle(null);
      }
    });

    binding.btnSendNormalNotification.setOnClickListener(View -> {
      manager.notify(NORMAL_NOTIFICATION_ID, normalNotificationBuilder.build());
    });

  }

  private void createNotification() {
//    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_baseline_contact_mail_24);
    Intent intent = new Intent(this, CommonIntentActivity.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
    Intent intentAction = new Intent(this, MainActivity.class);
    PendingIntent pendingIntentAction = PendingIntent.getActivity(this, 0, intentAction, 0);

    normalNotificationBuilder = new NotificationCompat.Builder(this, CHANNEL_NORMAL_ID)
        .setSmallIcon(R.drawable.ic_baseline_radio_24)
//        .setLargeIcon(bitmap)
        .setContentTitle(CHANNEL_NORMAL_ID)
        .setContentText(NORMAL_NOTIFICATION_TEXT)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .addAction(R.drawable.ic_baseline_radio_24, "首页", pendingIntentAction)//最多只展示三个action
    ;
  }

  private void createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      NotificationChannel channel = new NotificationChannel(CHANNEL_NORMAL_ID, CHANNEL_NORMAL_ID, NotificationManager.IMPORTANCE_DEFAULT);
      channel.setDescription("只展示简单的通知");
      manager.createNotificationChannel(channel);
    }
  }

}