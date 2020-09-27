package com.lixueyang.exercise.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Magnifier;

import com.lixueyang.exercise.databinding.ActivityTextRelatedBinding;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 文本相关练习
 * 1、设置TextView可有自动调整文字的大小，目前只支持TextView
 * 2、下载字体，在xml中使用
 * 3、表情符号兼容 EmojiCompat(使用捆绑式字体配置)
 * 4、放大镜控件
 */
public class TextRelatedActivity extends AppCompatActivity {

  private ActivityTextRelatedBinding binding;

  public static void startTextRelatedActivity(Activity activity) {
    Intent intent = new Intent(activity, TextRelatedActivity.class);
    activity.startActivity(intent);
  }

  @RequiresApi(api = Build.VERSION_CODES.P)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityTextRelatedBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    initMagnifier();
  }

  @RequiresApi(api = Build.VERSION_CODES.P)
  private void initMagnifier() {
    Magnifier magnifier = new Magnifier(binding.textView);
    binding.textView.setOnTouchListener((View v, MotionEvent event) -> {
      switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
        case MotionEvent.ACTION_MOVE:
          final int[] viewPosition = new int[2];
          v.getLocationOnScreen(viewPosition);
          magnifier.show(event.getRawX() - viewPosition[0],
              event.getRawY() - viewPosition[1]);
          break;
        case MotionEvent.ACTION_UP:
          magnifier.dismiss();
      }
      return true;
    });
  }
}