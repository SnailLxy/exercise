package com.lixueyang.exercise.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.lixueyang.exercise.R;
import com.lixueyang.exercise.databinding.ActivityNewDocumentBinding;

import java.text.MessageFormat;

/***
 * 模拟app打开文档，配合RecentScreenActivity，测试“最近使用的应用”屏幕功能
 * 多窗口模式
 */
public class NewDocumentActivity extends AppCompatActivity {

  private int documentCount;
  private String documentStr;
  private ActivityNewDocumentBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_new_document);
    documentCount = getIntent().getIntExtra(RecentScreenActivity.KEY_EXTRA_NEW_DOCUMENT_COUNTER, 0);
    documentStr = "onCreate_" + MessageFormat.format(getString(R.string.exercise_document_num), documentCount);
    binding.tvDocumentNum.setText(documentStr);
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    //不使用多个任务时，多次打开NewDocumentActivity，会调用此方法，并覆盖上一个NewDocumentActivity
    documentCount = intent.getIntExtra(RecentScreenActivity.KEY_EXTRA_NEW_DOCUMENT_COUNTER, 0);
    documentStr = "onNewIntent_" + MessageFormat.format(getString(R.string.exercise_document_num), documentCount);
    binding.tvDocumentNum.setText(documentStr);
  }

  @Override
  public void onMultiWindowModeChanged(boolean isInMultiWindowMode, Configuration newConfig) {
    super.onMultiWindowModeChanged(isInMultiWindowMode, newConfig);
    //多窗口
  }

  @Override
  public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
    super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
    //画中画
  }
}