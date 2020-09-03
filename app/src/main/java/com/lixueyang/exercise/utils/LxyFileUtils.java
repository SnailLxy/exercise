package com.lixueyang.exercise.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created on 2020/9/2.
 */
public class LxyFileUtils {

  public static File createFile(Context context) {
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        .format(new Date());
    String imageFileName = "JPEG_" + timeStamp + "_";
    try {
      return File.createTempFile(imageFileName, ".jpg", context.getExternalFilesDir(Environment.DIRECTORY_PICTURES));
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
