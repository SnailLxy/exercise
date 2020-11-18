package com.lixueyang.exercise.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.lixueyang.exercise.R;
import com.lixueyang.exercise.databinding.ActivityPictureAndShapeBinding;
import com.lixueyang.exercise.utils.ImageUtils;

import java.util.logging.Logger;

public class PictureAndShapeActivity extends AppCompatActivity {
  public static final String TAG = "PictureAndShapeActivity";
  private ActivityPictureAndShapeBinding binding;
  private Bitmap originalBitmap;
  private Palette palette;

  public static void startPictureAndShapeActivity(Activity activity) {
    Intent intent = new Intent(activity, PictureAndShapeActivity.class);
    activity.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityPictureAndShapeBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    initImageView();
    createPaletteSync();
    initPaletteColor();
  }

  private void initImageView() {
    originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test_image);
    Log.e(TAG, "originalBitmap---size=" + originalBitmap.getByteCount());
    Bitmap compressBitmapQuality = ImageUtils.compressBitmapQuality(getResources(), R.drawable.test_image, 100);
    Log.e(TAG, "compressBitmapQuality---size=" + compressBitmapQuality.getByteCount());
    Bitmap compressBitmap = ImageUtils.compressBitmap(getResources(), R.drawable.test_image, 300, 300);
    Log.e(TAG, "compressBitmap---size=" + compressBitmap.getByteCount());
    Bitmap compressScaleBitmap = ImageUtils.compressScaleBitmap(getResources(), R.drawable.test_image, 300, 300);
    Log.e(TAG, "compressScaleBitmap---size=" + compressScaleBitmap.getByteCount());
    binding.ivOriginalPic.setImageBitmap(originalBitmap);
    binding.ivCompressBitmapQuality.setImageBitmap(compressBitmapQuality);
    binding.ivCompressBitmap.setImageBitmap(compressBitmap);
    binding.ivCompressScaleBitmap.setImageBitmap(compressScaleBitmap);
  }

  /**
   * 同步获取Palette
   *
   * @return
   */
  private void createPaletteSync() {
    palette = Palette.from(originalBitmap).generate();
  }

  private void createPaletteAsync() {
    Palette.from(originalBitmap).generate(new Palette.PaletteAsyncListener() {
      @Override
      public void onGenerated(@Nullable Palette p) {
        palette = p;
      }
    });
  }

  private void initPaletteColor() {
    Palette.Swatch lightVibrantSwitch = palette.getLightVibrantSwatch();
    if (lightVibrantSwitch != null) {
      binding.btnLightVibrant.setTextColor(lightVibrantSwitch.getTitleTextColor());
      binding.btnLightVibrant.setBackgroundColor(lightVibrantSwitch.getBodyTextColor());
      binding.btnLightVibrant.setOnClickListener(view -> {
        binding.clImage.setBackgroundColor(lightVibrantSwitch.getRgb());
      });
    }

    Palette.Swatch vibrantSwitch = palette.getVibrantSwatch();
    if (vibrantSwitch != null) {
      binding.btnVibrant.setTextColor(vibrantSwitch.getTitleTextColor());
      binding.btnVibrant.setBackgroundColor(vibrantSwitch.getBodyTextColor());
      binding.btnVibrant.setOnClickListener(view -> {
        binding.clImage.setBackgroundColor(vibrantSwitch.getRgb());
      });
    }
    Palette.Swatch darkVibrantSwitch = palette.getDarkVibrantSwatch();
    if (darkVibrantSwitch != null) {
      binding.btnDarkVibrant.setTextColor(darkVibrantSwitch.getTitleTextColor());
      binding.btnDarkVibrant.setBackgroundColor(darkVibrantSwitch.getBodyTextColor());
      binding.btnDarkVibrant.setOnClickListener(view -> {
        binding.clImage.setBackgroundColor(darkVibrantSwitch.getRgb());
      });
    }
    Palette.Swatch lightMutedSwitch = palette.getLightMutedSwatch();
    if (lightMutedSwitch != null) {
      binding.btnLightMuted.setTextColor(lightMutedSwitch.getTitleTextColor());
      binding.btnLightMuted.setBackgroundColor(lightMutedSwitch.getBodyTextColor());
      binding.btnLightMuted.setOnClickListener(view -> {
        binding.clImage.setBackgroundColor(lightMutedSwitch.getRgb());
      });
    }
    Palette.Swatch mutedSwitch = palette.getMutedSwatch();
    if (mutedSwitch != null) {
      binding.btnMuted.setTextColor(mutedSwitch.getTitleTextColor());
      binding.btnMuted.setBackgroundColor(mutedSwitch.getBodyTextColor());
      binding.btnMuted.setOnClickListener(view -> {
        binding.clImage.setBackgroundColor(mutedSwitch.getRgb());
      });
    }
    Palette.Swatch darkMutedSwitch = palette.getDarkMutedSwatch();
    if (darkMutedSwitch != null) {
      binding.btnDarkMuted.setTextColor(darkMutedSwitch.getTitleTextColor());
      binding.btnDarkMuted.setBackgroundColor(darkMutedSwitch.getBodyTextColor());
      binding.btnDarkMuted.setOnClickListener(view -> {
        binding.clImage.setBackgroundColor(darkMutedSwitch.getRgb());
      });
    }
  }
}