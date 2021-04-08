package com.lixueyang.exercise.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.lixueyang.exercise.R;
import com.lixueyang.exercise.activity.animator.AnimatorActivity;
import com.lixueyang.exercise.activity.animator.PictureAndShapeActivity;
import com.lixueyang.exercise.activity.animator.ViewPagerAnimatorActivity;
import com.lixueyang.exercise.activity.commonintent.CommonIntentActivity;
import com.lixueyang.exercise.adapters.ActivityItemAdapter;
import com.lixueyang.exercise.databinding.ActivityMainBinding;
import com.lixueyang.exercise.models.ActivityItem;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MainActivity extends AppCompatActivity {

  private ActivityMainBinding binding;
  private List<ActivityItem> activityItemList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//保持屏幕开启
    initActivityItemList();
    initListener();

  }

  private void initActivityItemList() {
    activityItemList = new ArrayList<>();
    activityItemList.add(new ActivityItem(getString(R.string.exercise_storage_access_framework),
        () -> StorageAccessFrameworkActivity.startSAFActivity(MainActivity.this)));
    activityItemList.add(new ActivityItem(getString(R.string.exercise_two_way_binding),
        () -> TwoWayBindingActivity.startTwoWayBindingActivity(MainActivity.this)));
    activityItemList.add(new ActivityItem(getString(R.string.exercise_create_new_document),
        () -> RecentScreenActivity.startRecentScreenActivity(MainActivity.this)));
    activityItemList.add(new ActivityItem(getString(R.string.exercise_common_intent),
        () -> CommonIntentActivity.startCommonIntentActivity(MainActivity.this)));
    activityItemList.add(new ActivityItem(getString(R.string.exercise_motion_layout),
        () -> MotionLayoutActivity.startMotionLayoutActivity(MainActivity.this)));
    activityItemList.add(new ActivityItem(getString(R.string.exercise_text_related),
        () -> TextRelatedActivity.startTextRelatedActivity(MainActivity.this)));
    activityItemList.add(new ActivityItem(getString(R.string.exercise_notification),
        () -> NotificationActivity.startNotificationActivity(MainActivity.this)));
    activityItemList.add(new ActivityItem(getString(R.string.exercise_picture_and_shape),
        () -> PictureAndShapeActivity.startPictureAndShapeActivity(MainActivity.this)));
    activityItemList.add(new ActivityItem(getString(R.string.exercise_animator),
        () -> AnimatorActivity.startAnimatorActivity(MainActivity.this)));
    activityItemList.add(new ActivityItem(getString(R.string.exercise_transition),
        () -> startActivity(new Intent(MainActivity.this, TransitionActivity.class))));
    activityItemList.add(new ActivityItem(getString(R.string.exercise_view_page_animator),
        () -> startActivity(new Intent(MainActivity.this, ViewPagerAnimatorActivity.class))));
  }

  private void initListener() {
    binding.rvActivityItem.setLayoutManager(new LinearLayoutManager(this));
    ActivityItemAdapter activityItemAdapter = new ActivityItemAdapter(this, activityItemList);
    binding.rvActivityItem.setAdapter(activityItemAdapter);
  }
}