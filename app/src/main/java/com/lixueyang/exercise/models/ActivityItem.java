package com.lixueyang.exercise.models;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created on 2020/11/23.
 */
public class ActivityItem {

  private String activityTitle;
  private Activity activity;
  private Class targetActivity;
  private Bundle bundle;
  private Runnable runnable;

  public ActivityItem(Activity activity, Class targetActivity, String activityTitle) {
    this(activity, targetActivity, activityTitle, null);
  }

  public ActivityItem(String activityTitle, Runnable runnable) {
    this.activityTitle = activityTitle;
    this.runnable = runnable;
  }

  public ActivityItem(Activity activity, Class targetActivity, String activityTitle, Bundle bundle) {
    this.activityTitle = activityTitle;
    this.activity = activity;
    this.bundle = bundle;
    this.runnable = () -> {
      Intent intent = new Intent(activity, targetActivity);
      if (bundle != null) {
        intent.putExtras(bundle);
      }
      activity.startActivity(intent);
    };
  }

  public String getActivityTitle() {
    return activityTitle;
  }

  public void setActivityTitle(String activityTitle) {
    this.activityTitle = activityTitle;
  }

  public Runnable getRunnable() {
    return runnable;
  }

  public void setRunnable(Runnable runnable) {
    this.runnable = runnable;
  }
}
