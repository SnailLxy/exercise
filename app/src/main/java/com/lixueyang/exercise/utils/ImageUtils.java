package com.lixueyang.exercise.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;

/**
 * Created on 2020/9/3.
 */
public class ImageUtils {

  /**
   * 采样率压缩
   * 原理：图片尺寸的修改其实就是通过修改像素数
   * 采用临近采样：
   * x（x 为 2 的倍数）个像素最后对应一个像素。比如采样率设置为1/2，所以是两个像素生成一个像素。
   * 邻近采样的方式比较粗暴，直接选择其中的一个像素作为生成像素，另一个像素直接抛弃。
   *
   * @param res
   * @param id
   * @param targetWidth
   * @param targetHeight
   * @return
   */
  public static Bitmap compressBitmap(Resources res, int id, int targetWidth, int targetHeight) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeResource(res, id, options);
    int sampleSize = Math.max(options.outWidth / targetWidth, options.outHeight / targetHeight);
    options.inJustDecodeBounds = false;
    options.inSampleSize = sampleSize;
    return BitmapFactory.decodeResource(res, id, options);
  }

  /**
   * 质量压缩
   * 原理：质量压缩不会减少图片的像素，它是在保持像素的前提下改变图片的位深及透明度，来达到压缩图片的目的。
   * 图片大小不变
   *
   * @param res
   * @param id
   * @param quality 为0～100
   * @return
   */
  public static Bitmap compressBitmapQuality(Resources res, int id, int quality) {
    Bitmap bitmap = BitmapFactory.decodeResource(res, id);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
    byte[] bytes = outputStream.toByteArray();
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
  }

  /**
   * 缩放法压缩
   * 原理：使用双线性采样
   * 这个算法不参考了源像素相应位置周围 2x2 个点的值，根据相对位置取对应的权重，经过计算之后得到目标图像。
   *
   * @param res
   * @param id
   * @param targetWidth
   * @param targetHeight
   * @return
   */
  public static Bitmap compressScaleBitmap(Resources res, int id, int targetWidth, int targetHeight) {
    Bitmap bitmap = BitmapFactory.decodeResource(res, id);
    return Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true);
  }
}
