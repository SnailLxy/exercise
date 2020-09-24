package com.lixueyang.exercise.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/1/24.
 */
public class FlowRadioGroup extends RadioGroup {

  private List<Rect> childrenBounds = new ArrayList<>();
  private Rect rect;

  public FlowRadioGroup(Context context) {
    super(context);
  }

  public FlowRadioGroup(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int widthSize = MeasureSpec.getSize(widthMeasureSpec);
    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
    int heightSize = MeasureSpec.getSize(heightMeasureSpec);
    int heightMode = MeasureSpec.getMode(heightMeasureSpec);

    measureChildren(widthMeasureSpec, heightMeasureSpec);

    int childCount = getChildCount();
    int maxWidth = 0;
    int maxHeight = 0;
    int maxLineHeight = 0;//当前行最大高度
    int maxLineWidth = 0;//当前行最大宽度
    for (int i = 0; i < childCount; i++) {
      View view = getChildAt(i);
      LayoutParams params = (LayoutParams) view.getLayoutParams();
      int childWidth = view.getMeasuredWidth() + params.leftMargin + params.rightMargin;
      int childHeight = view.getMeasuredHeight() + params.topMargin + params.bottomMargin;
      if (maxLineWidth + childWidth <= widthSize) {
        maxLineWidth += childWidth;
        if (maxLineHeight < childHeight) {
          maxLineHeight = childHeight;
        }
      } else {
        //换行
        if (maxLineWidth > maxWidth) {
          maxWidth = maxLineWidth;
        }
        maxHeight += maxLineHeight;

        maxLineWidth = childWidth;
        maxLineHeight = childHeight;
      }
      rect = new Rect(maxLineWidth - view.getMeasuredWidth() - params.rightMargin,
          maxHeight + params.topMargin,
          maxLineWidth - params.rightMargin,
          maxHeight + view.getMeasuredHeight() + params.topMargin);
      childrenBounds.add(rect);
      //最后一行处理
      if (i == childCount - 1) {
        if (maxLineWidth > maxWidth) {
          maxWidth = maxLineWidth;
        }
        maxHeight += maxLineHeight;
      }
    }
    //加上padding
    maxWidth += getPaddingLeft() + getPaddingRight();
    maxHeight += getPaddingTop() + getPaddingBottom();
    setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : maxWidth,
        heightMode == MeasureSpec.EXACTLY ? heightSize : maxHeight);

  }


  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    int count = getChildCount();
    for (int i = 0; i < count; i++) {
      Rect rect = childrenBounds.get(i);
      getChildAt(i).layout(rect.left, rect.top, rect.right, rect.bottom);
    }
  }

}
