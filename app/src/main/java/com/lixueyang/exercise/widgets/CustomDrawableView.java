package com.lixueyang.exercise.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * Created on 2020/10/26.
 * 这个ShapeDrawable直接使用drawable.xml更合适
 */
public class CustomDrawableView extends AppCompatImageView {

  private ShapeDrawable drawable;

  public CustomDrawableView(@NonNull Context context) {
    this(context, null);
  }

  public CustomDrawableView(@NonNull Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CustomDrawableView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    drawable = new ShapeDrawable(new OvalShape());
    drawable.getPaint().setColor(0xffF8DF70);
    drawable.setBounds(0, 0, 300, 300);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    drawable.draw(canvas);
  }
}
