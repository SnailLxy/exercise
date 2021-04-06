package com.lixueyang.exercise.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.Toast;

import com.lixueyang.exercise.databinding.ActivityAnimatorBinding;

import java.util.logging.Logger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

public class AnimatorActivity extends AppCompatActivity {

  public static final String TAG = "AnimatorActivity";
  private ActivityAnimatorBinding binding;
  private ValueAnimator valueAnimator;
  private ObjectAnimator translationYAnimator;
  private ObjectAnimator fadeAnimator;
  private SpringAnimation springAnimationY;
  private SpringAnimation springAnimationY1;
  private SpringAnimation springAnimationX;
  private SpringAnimation springAnimationX1;

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
    initCircularRevealAnimation();
    initSpringAnimation();
    ObjectAnimator animator = ObjectAnimator.ofInt(binding.pbTime, "progress", 0, 100)
        .setDuration(DateUtils.MINUTE_IN_MILLIS);
    animator.start();
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
      Path path = new Path();
      //在left, top, right, bottom范围内，以其中心为圆心，从startAngle角度开始，顺时针旋转sweepAngle度
      path.arcTo(100f, 0f, 500f, 500f, 0f, 180f, true);
      ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.X, View.Y, path);
      animator.setDuration(2000);
      animator.start();
    });
  }

  private void initCircularRevealAnimation() {
    binding.tvCircularReveal.setOnClickListener(view -> {
      int cx = view.getWidth() / 2;
      int cy = view.getHeight() / 2;
      Animator animator = ViewAnimationUtils.createCircularReveal(view, cx, cy, cx, 0f);
      view.setVisibility(View.GONE);
      //隐藏的话，最好是不要使用onAnimationEnd，因为这时，会出现控件最开始的界面
//      animator.addListener(new AnimatorListenerAdapter() {
//        @Override
//        public void onAnimationEnd(Animator animation) {
//          super.onAnimationEnd(animation);
//          view.setVisibility(View.GONE);
//        }
//      });
      animator.start();
    });
  }

  private void initSpringAnimation() {
    springAnimationY = new SpringAnimation(binding.ivAnimatorSpring, DynamicAnimation.Y, 100);
    springAnimationX = new SpringAnimation(binding.ivAnimatorSpring, DynamicAnimation.X, 100);
    springAnimationY1 = new SpringAnimation(binding.ivAnimatorSpring1, DynamicAnimation.Y, 0);
    springAnimationX1 = new SpringAnimation(binding.ivAnimatorSpring1, DynamicAnimation.X, 0);
    springAnimationX.setStartVelocity(10);
//    springAnimationX.getSpring().setDampingRatio(0);//为0时会一直晃动，不会停止。可以做永不停止的往复动画。不过不建议这么做
    springAnimationX.getSpring().setDampingRatio(0.1f);
    springAnimationY.setStartVelocity(10);
    springAnimationY.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);

    springAnimationY1.getSpring().setDampingRatio(10f);
    springAnimationY1.getSpring().setStiffness(SpringForce.STIFFNESS_HIGH);
    springAnimationX1.getSpring().setDampingRatio(10f);

    springAnimationY.addUpdateListener((animation, value, velocity) -> {
      springAnimationY1.animateToFinalPosition(value);
    });
    springAnimationX.addUpdateListener((animation, value, velocity) -> {
      springAnimationX1.animateToFinalPosition(value);
    });

    binding.ivAnimatorSpring.setOnClickListener(view -> {
      springAnimationX.start();
      springAnimationY.start();
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
    springAnimationX.cancel();
    springAnimationY.cancel();
    springAnimationX1.cancel();
    springAnimationY1.cancel();
  }
}