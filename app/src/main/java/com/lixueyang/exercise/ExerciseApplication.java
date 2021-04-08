package com.lixueyang.exercise;

import android.app.Application;

import java.util.logging.Logger;

import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;

/**
 * Created on 2020/9/25.
 */
public class ExerciseApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    EmojiCompat.Config config = new BundledEmojiCompatConfig(this);
    EmojiCompat.init(config);
  }
}
