package com.lixueyang.exercise.activity.commonintent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.RadioGroup;

import com.lixueyang.exercise.R;
import com.lixueyang.exercise.databinding.ActivitySendIntentBinding;
import com.lixueyang.exercise.databinding.ActivitySettingIntentBinding;

/**
 * 打开设置页相关Intent
 * 这里只列举了一些，其他的可以查看Settings类中的action
 */
public class SettingIntentActivity extends AppCompatActivity {

  private ActivitySettingIntentBinding binding;
  private String settingAction = Settings.ACTION_SETTINGS;

  public static void startSettingIntentActivity(Activity activity) {
    Intent intent = new Intent(activity, SettingIntentActivity.class);
    activity.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivitySettingIntentBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    binding.btnOpenSetting.setOnClickListener(view -> {
      Intent intent = new Intent(settingAction);
      if (intent.resolveActivity(getPackageManager()) != null) {
        startActivity(intent);
      }
    });

    binding.frgSettingAction.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
          case R.id.rb_settings:
            settingAction = Settings.ACTION_SETTINGS;
            break;
          case R.id.rb_wireless_settings:
            settingAction = Settings.ACTION_WIRELESS_SETTINGS;
            break;
          case R.id.rb_airplane_mode_settings:
            settingAction = Settings.ACTION_AIRPLANE_MODE_SETTINGS;
            break;
          case R.id.rb_wifi_settings:
            settingAction = Settings.ACTION_WIFI_SETTINGS;
            break;
          case R.id.rb_apn_settings:
            settingAction = Settings.ACTION_APN_SETTINGS;
            break;
          case R.id.rb_bluetooth_settings:
            settingAction = Settings.ACTION_BLUETOOTH_SETTINGS;
            break;
          case R.id.rb_date_settings:
            settingAction = Settings.ACTION_DATE_SETTINGS;
            break;
          case R.id.rb_locale_settings:
            settingAction = Settings.ACTION_LOCALE_SETTINGS;
            break;
          case R.id.rb_input_mathod_settings:
            settingAction = Settings.ACTION_INPUT_METHOD_SETTINGS;
            break;
          case R.id.rb_display_settings:
            settingAction = Settings.ACTION_DISPLAY_SETTINGS;
            break;
          case R.id.rb_security_settings:
            settingAction = Settings.ACTION_SECURITY_SETTINGS;
            break;
          case R.id.rb_location_source_settings:
            settingAction = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
            break;
          case R.id.rb_internal_storage_settings:
            settingAction = Settings.ACTION_INTERNAL_STORAGE_SETTINGS;
            break;
          case R.id.rb_memory_card_settings:
            settingAction = Settings.ACTION_MEMORY_CARD_SETTINGS;
            break;

        }
      }
    });
  }
}