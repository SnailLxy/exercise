package com.lixueyang.exercise.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;

/**
 * Created on 2019/1/24.
 */
//todo onMeasure和onLayout里做的事情有重复,优化下性能只做一次运算
public class FlowRadioGroup extends RadioGroup {

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
    //pre为前面所有的child的相加后的位置
    int preLeft = getPaddingLeft();
    int preTop = getPaddingTop();
    //记录每一行的最高值
    int maxHeight = 0;
    for (int i = 0; i < count; i++) {
      View child = getChildAt(i);
      LayoutParams params = (LayoutParams) child.getLayoutParams();
      //r-l为当前容器的宽度。如果子view的累积宽度大于容器宽度，就换行。
      if (preLeft + params.leftMargin + child.getMeasuredWidth() + params.rightMargin + getPaddingRight() > (r - l)) {
        //重置
        preLeft = getPaddingLeft();
        //要选择child的height最大的作为设置
        preTop = preTop + maxHeight;
        maxHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;
      } else { //不换行,计算最大高度
        maxHeight = Math.max(maxHeight, child.getMeasuredHeight() + params.topMargin + params.bottomMargin);
      }
      int left = preLeft + params.leftMargin;
      int top = preTop + params.topMargin;
      int right = left + child.getMeasuredWidth();
      int bottom = top + child.getMeasuredHeight();
      //为子view布局
      child.layout(left, top, right, bottom);
      //计算布局结束后，preLeft的值
      preLeft += params.leftMargin + child.getMeasuredWidth() + params.rightMargin;
    }
  }

}
