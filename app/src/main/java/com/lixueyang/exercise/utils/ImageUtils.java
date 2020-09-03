package com.lixueyang.exercise.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created on 2020/9/3.
 */
public class ImageUtils {

  public static Bitmap compressBitmap(String imagePath, int targetWidth, int targetHeight) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(imagePath, options);
    int sampleSize = Math.max(options.outWidth / targetWidth, options.outHeight / targetHeight);
    options.inJustDecodeBounds = false;
    options.inSampleSize = sampleSize;
    return BitmapFactory.decodeFile(imagePath, options);
  }

}
