package com.lixueyang.exercise.activity.commonintent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.lixueyang.exercise.R;
import com.lixueyang.exercise.databinding.ActivityMapIntentBinding;

public class MapIntentActivity extends AppCompatActivity {

  private ActivityMapIntentBinding binding;

  public static void startMapIntentActivity(Activity activity) {
    Intent intent = new Intent(activity, MapIntentActivity.class);
    activity.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMapIntentBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    binding.btnOpenMap.setOnClickListener(view -> {
      if (TextUtils.isEmpty(binding.etAddress.getText())) {
        Toast.makeText(this, "请输入地址", Toast.LENGTH_LONG).show();
        return;
      }
      String address = Uri.encode(binding.etAddress.getText().toString());
      //根据地名打开地图应用显示
      Uri geoLocation = Uri
          .parse("geo:0,0?q=" + address);
      //根据经纬度打开地图
//     Uri geoLocation = Uri.parse("geo:26.5789070770,106.7170012064?z=11");
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setData(geoLocation);
      if (intent.resolveActivity(getPackageManager()) != null) {
        startActivity(intent);
      }
    });
  }
}