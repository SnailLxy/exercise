package com.lixueyang.exercise.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.lixueyang.exercise.databinding.ActivityAnimatorBinding;

public class AnimatorActivity extends AppCompatActivity {

  private ActivityAnimatorBinding binding;
  private ValueAnimator valueAnimator;
  private ObjectAnimator translationYAnimator;
  private ObjectAnimator fadeAnimator;

  public static void startAnimatorActivity(Activity activity) {
    Intent intent = new Intent(activity, AnimatorActivity.class);
    activity.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityAnimatorBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    initValueAnimator();
    initObjectAnimator();
    initViewGroupAnimator();

    initKeyframeView();
  }


  private void initValueAnimator() {
    valueAnimator = ValueAnimator.ofInt(0, 60);
    valueAnimator.setDuration(50 * 1000);
    valueAnimator.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationStart(Animator animation) {
        super.onAnimationStart(animation);
        binding.tvShowAnimator.setText("倒计时开始");
      }

      @Override
      public void onAnimationStart(Animator animation, boolean isReverse) {

      }

      @Override
      public void onAnimationPause(Animator animation) {
        super.onAnimationPause(animation);
      }

      @Override
      public void onAnimationResume(Animator animation) {
        super.onAnimationResume(animation);
      }

      @Override
      public void onAnimationRepeat(Animator animation) {
        super.onAnimationRepeat(animation);
      }

      @Override
      public void onAnimationCancel(Animator animation) {
        super.onAnimationCancel(animation);
      }

      @Override
      public void onAnimationEnd(Animator animation, boolean isReverse) {

      }

      @Override
      public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        binding.tvShowAnimator.setText("倒计时结束");
      }
    });
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        int time = (Integer) animation.getAnimatedValue();
        binding.tvShowAnimator.setText(time + " 秒 ");
//        if (time > 20) {
//          animation.cancel();
//        }
      }
    });
    valueAnimator.setInterpolator(new LinearInterpolator());
    valueAnimator.start();

  }

  private void initObjectAnimator() {
    translationYAnimator = ObjectAnimator
        .ofFloat(binding.ivAnimator, "translationY", 100f, 800f);
    translationYAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
    translationYAnimator.setDuration(5 * 1000);

    fadeAnimator = ObjectAnimator.ofFloat(binding.ivAnimator, "alpha", 1f, 0.2f);
    fadeAnimator.setDuration(1000);
    AnimatorSet animatorSet = new AnimatorSet();
    animatorSet.playTogether(translationYAnimator, fadeAnimator);
    animatorSet.setDuration(10 * 1000);//会覆盖包含的动画的时间
    animatorSet.start();
  }

  private void initViewGroupAnimator() {
    binding.btnDelete.setOnClickListener(view -> {
      binding.ivAnimator.setVisibility(View.GONE);
      //使用ViewPropertyAnimator进行多个属性的动画，改变的是view的属性,
      // ⚠️这个目前测试只找到动画同时执行的情况，没有找到控制先后顺序的效果
      binding.tvKeyframe.animate().alpha(0.5f).translationY(300f);
    });
  }

  private void initKeyframeView() {
    Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
    Keyframe kf1 = Keyframe.ofFloat(0.8f, 90f);
    Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
    PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2);
    ObjectAnimator rotationAnim = ObjectAnimator.ofPropertyValuesHolder(binding.tvKeyframe, pvhRotation);
    rotationAnim.setDuration(5000);
    rotationAnim.start();

    binding.tvKeyframe.setOnClickListener(view -> {
      Toast.makeText(AnimatorActivity.this, "ViewPropertyAnimator效果", Toast.LENGTH_LONG).show();
    });
  }

  @Override
  protected void onPause() {
    super.onPause();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    valueAnimator.cancel();
    translationYAnimator.cancel();
  }
}