package com.lixueyang.exercise.activity.commonintent;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.lixueyang.exercise.databinding.ActivityCallIntentBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

/**
 * 拨打电话相关intent
 * 1、Intent.ACTION_DIAL，只是打开打电话应用
 * 2、Intent.ACTION_CALL，直接拨打电话，需要拨打电话权限
 * <uses-permission android:name="android.permission.CALL_PHONE" />
 */
public class CallIntentActivity extends AppCompatActivity {

  private static final int CALL_REQUEST_CODE = 101;
  private ActivityCallIntentBinding binding;
  private String[] permissions = {
      Manifest.permission.CALL_PHONE
  };

  public static void startCallIntentActivity(Activity activity) {
    Intent intent = new Intent(activity, CallIntentActivity.class);
    activity.startActivity(intent);
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityCallIntentBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    binding.btnOpenCallApp.setOnClickListener(view -> {
      Intent intent = new Intent(Intent.ACTION_DIAL);
      intent.setData(Uri.parse("tel:" + binding.etPhoneNumber.getText()));
      if (intent.resolveActivity(getPackageManager()) != null) {
        startActivity(intent);
      }
    });

    binding.btnCall.setOnClickListener(view -> {
      int hasCallPhonePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
      if (hasCallPhonePermission == PackageManager.PERMISSION_GRANTED) {
        gotoCall();
      } else {
        ActivityCompat.requestPermissions(this, permissions, CALL_REQUEST_CODE);
      }
    });
  }

  private void gotoCall() {
    Intent intent = new Intent(Intent.ACTION_CALL);
    intent.setData(Uri.parse("tel:" + binding.etPhoneNumber.getText()));
    if (intent.resolveActivity(getPackageManager()) != null) {
      startActivity(intent);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == CALL_REQUEST_CODE) {
      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        gotoCall();
      } else {
        Toast.makeText(this, "没有电话权限本功能不可使用", Toast.LENGTH_LONG).show();
      }
    }

  }
}