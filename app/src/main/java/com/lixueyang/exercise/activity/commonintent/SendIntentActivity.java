package com.lixueyang.exercise.activity.commonintent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.lixueyang.exercise.databinding.ActivitySendIntentBinding;

/**
 * 发送相关的intent，这里练习发邮件或短信
 * 1、ACTION_SENDTO （适用于不带附件）
 * ACTION_SEND（适用于带一个附件）
 * ACTION_SEND_MULTIPLE（适用于带多个附件）
 * 2、添加附件时，使用Intent.EXTRA_STREAM，对应附件的uri,多个附件则是List
 */
public class SendIntentActivity extends AppCompatActivity {

  private ActivitySendIntentBinding binding;

  public static void startSendIntentActivity(Activity activity) {
    Intent intent = new Intent(activity, SendIntentActivity.class);
    activity.startActivity(intent);
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivitySendIntentBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    initView();
  }

  private void initView() {
    binding.btnSendEmail.setOnClickListener(view -> {
      //不带附件
      Intent intent = new Intent(Intent.ACTION_SENDTO);
      intent.setData(Uri.parse("mailto:"));//限制只有邮箱应用处理
      intent.putExtra(Intent.EXTRA_EMAIL, binding.etEmailNo.getText());
      intent.putExtra(Intent.EXTRA_SUBJECT, binding.etSendSubject.getText());
      intent.putExtra(Intent.EXTRA_TEXT, binding.etSendContent.getText());
      if (intent.resolveActivity(getPackageManager()) != null) {
        startActivity(intent);
      }
    });

    binding.btnSendSms.setOnClickListener(view -> {
      Intent intent = new Intent(Intent.ACTION_SENDTO);
      intent.setData(Uri.parse("smsto:"));//限制只有短信应用可以处理
      intent.putExtra("subject", binding.etSendSubject.getText());
      intent.putExtra("sms_body", binding.etSendContent.getText());
      if (intent.resolveActivity(getPackageManager()) != null) {
        startActivity(intent);
      }
    });
  }
}