package com.lixueyang.exercise.utils;

import android.content.Intent;
import android.content.pm.PackageManager;

/**
 * Created on 2020/9/2.
 */
public class IntentUtils {

  /**
   * 隐式Intent是否有接收者
   * @param intent
   * @param packageManager
   * @return
   */
  public static boolean isActivityAlive(Intent intent, PackageManager packageManager) {
    return intent.resolveActivity(packageManager) != null;
  }
}
