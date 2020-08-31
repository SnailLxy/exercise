package com.lixueyang.exercise.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lixueyang.exercise.R;
import com.lixueyang.exercise.databinding.ActivityRecentsScreenBinding;

import java.util.function.IntUnaryOperator;

/**
 * “最近使用的应用”屏幕
 */
public class RecentScreenActivity extends AppCompatActivity {

  public static final String KEY_EXTRA_NEW_DOCUMENT_COUNTER = "KEY_EXTRA_NEW_DOCUMENT_COUNTER";
  private ActivityRecentsScreenBinding binding;
  private int documentCounter = 0;

  public static void startRecentScreenActivity(Activity activity) {
    Intent intent = new Intent(activity, RecentScreenActivity.class);
    activity.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_recents_screen);
    binding.btnCreateNewDocument.setOnClickListener(view -> createNewDocument());
  }

  public void createNewDocument() {
    final Intent newDocumentIntent = new Intent(this, NewDocumentActivity.class);
    newDocumentIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
    if (binding.cbUseMultipleTasks.isChecked()) {
      newDocumentIntent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
    }
    newDocumentIntent.putExtra(KEY_EXTRA_NEW_DOCUMENT_COUNTER, documentCounter++);
    startActivity(newDocumentIntent);
  }
}