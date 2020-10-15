package com.lixueyang.exercise.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RemoteViews;

import com.lixueyang.exercise.R;
import com.lixueyang.exercise.activity.commonintent.CommonIntentActivity;
import com.lixueyang.exercise.databinding.ActivityNotificationBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.Person;
import androidx.core.app.RemoteInput;
import androidx.core.app.TaskStackBuilder;

public class NotificationActivity extends AppCompatActivity {

  private static final String CHANNEL_NORMAL_ID = "101";
  private static final String CHANNEL_REPLY_ID = "102";
  private static final String CHANNEL_TALK_ID = "103";
  private static final String CHANNEL_NORMAL_NAME = "简单通知";
  private static final String CHANNEL_REPLY_NAME = "可回复通知";
  private static final String CHANNEL_TALK_NAME = "对话通知";
  private static final String KEY_TEXT_REPLY = "key_text_reply";
  private static final String GROUP_KEY_NORMAL = "com.lixueyang.exercise.WORK_EMAIL";
  private static final int NORMAL_NOTIFICATION_ID = 101;
  private static final int REPLY_NOTIFICATION_ID = 102;
  private static final int TALK_NOTIFICATION_ID = 103;

  private ActivityNotificationBinding binding;

  private NotificationManager manager;
  private NotificationCompat.Builder normalNotificationBuilder;
  private NotificationCompat.Builder replyNotificationBuilder;
  private NotificationCompat.Builder talkNotificationBuilder;
  private NotificationCompat.Builder musicNotificationBuilder;
  private NotificationCompat.Builder customNotificationBuilder;
  private Notification.Builder musicNotificationBuilderForO;

  private PendingIntent contentPendingIntent;
  private PendingIntent actionPendingIntent;
  private PendingIntent actionPendingIntent2;
  private PendingIntent replyPendingIntent;

  private Bitmap bigPicBitmap;

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
    bigPicBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.notification_big);//svg图片转换成bitmap，不是用这个方法
    initView();
    initPendingIntent();
    createNotification();
    createNotificationChannel();
  }

  private void initView() {
    binding.rgNormalNotificationStyle.setOnCheckedChangeListener((RadioGroup group, int checkedId) -> {
      switch (checkedId) {
        case R.id.rb_show_big_text:
          normalNotificationBuilder.setStyle(new NotificationCompat.BigTextStyle()
              .bigText(getString(R.string.exercise_notification_normal_content)));
          break;
        case R.id.rb_show_big_pic:
          normalNotificationBuilder.setStyle(new NotificationCompat.BigPictureStyle()
              .bigPicture(bigPicBitmap));
          break;
        case R.id.rb_show_inbox:
          normalNotificationBuilder.setStyle(new NotificationCompat.InboxStyle()
              .addLine("这样行看看是什么情况")
              .addLine("第二行")
              .addLine("第三行")
              .addLine(getString(R.string.exercise_notification_normal_content))
              .addLine("最后一行"));
          break;
      }
    });

    binding.btnSendNormalNotification.setOnClickListener(View -> {
      manager.notify(NORMAL_NOTIFICATION_ID, normalNotificationBuilder.build());
    });

    binding.btnSendReplyNotification.setOnClickListener(View -> {
      manager.notify(REPLY_NOTIFICATION_ID, replyNotificationBuilder.build());
    });

    binding.btnSendTalkNotification.setOnClickListener(view -> {
      manager.notify(TALK_NOTIFICATION_ID, talkNotificationBuilder.build());
    });

    binding.btnSendMusicNotification.setOnClickListener(view -> {
      manager.notify(NORMAL_NOTIFICATION_ID, musicNotificationBuilderForO.build());
    });

    binding.btnSendCustomNotification.setOnClickListener(view -> {
      manager.notify(NORMAL_NOTIFICATION_ID, customNotificationBuilder.build());
    });
  }

  private void initPendingIntent() {
    Intent intent = new Intent(this, MainActivity.class);
    contentPendingIntent = PendingIntent.getActivity(this, 101, intent, 0);

    /**
     * 不需要返回堆栈
     */
    Intent intentAction1 = new Intent(this, CommonIntentActivity.class);
    actionPendingIntent = PendingIntent.getActivity(this, 102, intentAction1, 0);
/**
 * 带有返回栈的
 * todo 目前测试打开并不会带有返回栈，是无效的。9.0与10.0具体表现不同，9带之前的见面的返回栈，10不带
 */
    Intent intentAction2 = new Intent(this, NewDocumentActivity.class);
    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
    stackBuilder.addNextIntent(intentAction2);
    actionPendingIntent2 = stackBuilder.getPendingIntent(102, PendingIntent.FLAG_UPDATE_CURRENT);

    Intent intentReply = new Intent(this, NotificationActivity.class);
    replyPendingIntent = PendingIntent.getActivity(this, 103, intentReply, 0);
  }

  private void createNotification() {
    createNormalNotification();
    createReplyNotification();
    createTalkNotification();
    createMusicNotification();
    createCustomNotification();
  }

  private void createNormalNotification() {
    normalNotificationBuilder = new NotificationCompat.Builder(this, CHANNEL_NORMAL_ID)
        .setSmallIcon(R.drawable.ic_baseline_radio_24)
        .setLargeIcon(bigPicBitmap)
        .setContentTitle(getString(R.string.exercise_notification_normal_title))
        .setContentText(binding.etNotificationContent.getText())
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(contentPendingIntent)
        .setProgress(100, 20, false) //这里只展示进度条，就不实现功能了，其实就是不断修改progress，并notify
        .setAutoCancel(true)
        .setGroup(GROUP_KEY_NORMAL)//创建通知组并为其添加通知，todo 目前不起作用
        .setGroupSummary(true)
        .addAction(R.drawable.ic_baseline_radio_24, getString(R.string.exercise_common_intent), actionPendingIntent)//最多只展示三个action
        .addAction(R.drawable.ic_baseline_radio_24, getString(R.string.exercise_create_new_document), actionPendingIntent2);
  }

  private void createReplyNotification() {
    RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
        .setLabel(getString(R.string.exercise_notification_replay_hint))
        .build();

    NotificationCompat.Action action = new NotificationCompat.Action
        .Builder(R.drawable.ic_baseline_send_24, getString(R.string.exercise_notification_replay_label), replyPendingIntent)
        .addRemoteInput(remoteInput)
        .build();

    replyNotificationBuilder = new NotificationCompat.Builder(this, CHANNEL_REPLY_ID)
        .setContentTitle(getString(R.string.exercise_notification_replay_title))
        .setSmallIcon(R.drawable.ic_baseline_chat_24)
        .setContentText(getString(R.string.exercise_notification_replay_content))
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .addAction(action);
  }

  /**
   * NotificationCompat中没有MediaStyle，导致无效，后面查下有没有别的
   */
  private void createMusicNotification() {
//    musicNotificationBuilder = new NotificationCompat.Builder(this, CHANNEL_NORMAL_ID)
//        .setSmallIcon(R.drawable.ic_baseline_music_video_24)
//        .setLargeIcon(bigPicBitmap)
//        .setContentTitle("Wonderful music")
//        .setContentText("My Awesome Band")
//        .addAction(R.drawable.ic_baseline_skip_previous_24, "Previous", contentPendingIntent)
//        .addAction(R.drawable.ic_baseline_pause_24, "Pause", contentPendingIntent)
//        .addAction(R.drawable.ic_baseline_skip_next_24, "Next", contentPendingIntent)
//        .setStyle(new Notification.MediaStyle().setShowActionsInCompactView(1));

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      musicNotificationBuilderForO = new Notification.Builder(this, CHANNEL_NORMAL_ID)
          .setSmallIcon(R.drawable.ic_baseline_music_video_24)
          .setLargeIcon(bigPicBitmap)
          .setContentTitle("Wonderful music")
          .setContentText("My Awesome Band")
          .addAction(R.drawable.ic_baseline_skip_previous_24, "Previous", contentPendingIntent)
          .addAction(R.drawable.ic_baseline_pause_24, "Pause", contentPendingIntent)
          .addAction(R.drawable.ic_baseline_skip_next_24, "Next", contentPendingIntent)
          .setStyle(new Notification.MediaStyle().setShowActionsInCompactView(1));
    }
  }

  /**
   * 通讯应用提示用户有新消息
   */
  private void createTalkNotification() {
    Person person1 = new Person.Builder().setName("李雪洋").build();
    NotificationCompat.MessagingStyle.Message message1 = new NotificationCompat.MessagingStyle.Message("你好，请问你是？", System.currentTimeMillis(), person1);
    Person person2 = new Person.Builder().setName("无名氏").build();
    NotificationCompat.MessagingStyle.Message message2 = new NotificationCompat.MessagingStyle.Message("我是无名氏！一个陌生人！", System.currentTimeMillis(), person2);

    talkNotificationBuilder = new NotificationCompat.Builder(this, CHANNEL_TALK_ID)
        .setSmallIcon(R.drawable.ic_baseline_chat_24)
        .setLargeIcon(bigPicBitmap)
        //此样式只适用于Android 7.0以上版本，这里使用的是NotificationCompat，所以不用适配
        .setStyle(new NotificationCompat.MessagingStyle(person1).addMessage(message1).addMessage(message2))
        .setContentIntent(contentPendingIntent)
        .setGroup(GROUP_KEY_NORMAL)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
  }

  private void createCustomNotification() {
    RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.layout_notification_custom_small);
    notificationLayout.setOnClickPendingIntent(R.id.iv_skip_previous, actionPendingIntent);

    customNotificationBuilder = new NotificationCompat.Builder(this, CHANNEL_NORMAL_ID)
        .setSmallIcon(R.drawable.ic_baseline_music_video_24)
        .setContentTitle("Wonderful music")
        .setContentText("My Awesome Band")
        .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
        .setContent(notificationLayout)
    ;
  }

  /**
   * Android 8.0以上版本,对应的CHANNEL_NORMAL_ID必须有对应的channel才可显示通知
   */
  private void createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      List<NotificationChannel> channels = new ArrayList<>();

      NotificationChannel channel = new NotificationChannel(CHANNEL_NORMAL_ID, CHANNEL_NORMAL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
      channel.setDescription("只展示简单的通知");
      channels.add(channel);

      NotificationChannel replyChannel = new NotificationChannel(CHANNEL_REPLY_ID, CHANNEL_REPLY_NAME, NotificationManager.IMPORTANCE_LOW);
      replyChannel.setDescription("可回复的通知");
      replyChannel.setShowBadge(false);//启动器图标上不会显示通知标志，todo 现在在不同的版本上表现不同，有的显示，有的不显示，有的手机直接就不会展示
      channels.add(replyChannel);

      NotificationChannel talkChannel = new NotificationChannel(CHANNEL_TALK_ID, CHANNEL_TALK_NAME, NotificationManager.IMPORTANCE_HIGH);
      talkChannel.setDescription("显示对话的通知");
      talkChannel.setShowBadge(false);//启动器图标上不会显示通知标志
      channels.add(talkChannel);

      manager.createNotificationChannels(channels);
    }
  }

  private String getReplyText(Intent intent) {
    Bundle remoteInputBundle = RemoteInput.getResultsFromIntent(intent);
    if (remoteInputBundle != null) {
      return remoteInputBundle.getString(KEY_TEXT_REPLY);
    }
    return null;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        case 103:
          String replyText = getReplyText(data);
          //回复已收到并得到正确处理
          manager.notify(REPLY_NOTIFICATION_ID, replyNotificationBuilder.build());
          break;
      }
    }
  }
}