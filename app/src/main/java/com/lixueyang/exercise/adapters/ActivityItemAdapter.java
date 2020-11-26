package com.lixueyang.exercise.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lixueyang.exercise.R;
import com.lixueyang.exercise.databinding.EaItemStartActivityBinding;
import com.lixueyang.exercise.models.ActivityItem;
import com.lixueyang.exercise.utils.CollectionUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created on 2020/11/23.
 */
public class ActivityItemAdapter extends RecyclerView.Adapter<ActivityItemAdapter.ActivityItemViewHolder> {

  private Activity activity;
  private List<ActivityItem> activityItemList;

  public ActivityItemAdapter(Activity activity, List<ActivityItem> activityItemList) {
    this.activity = activity;
    this.activityItemList = activityItemList;
  }

  @NonNull
  @Override
  public ActivityItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(activity).inflate(R.layout.ea_item_start_activity, parent, false);
    return new ActivityItemViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ActivityItemViewHolder holder, int position) {
    ActivityItem item = activityItemList.get(position);
    holder.binding.btnGotoActivity.setText(item.getActivityTitle());
    holder.binding.btnGotoActivity.setOnClickListener(view -> item.getRunnable().run());
  }

  @Override
  public int getItemCount() {
    if (CollectionUtils.isEmpty(activityItemList)) {
      return 0;
    }
    return activityItemList.size();
  }


  public class ActivityItemViewHolder extends RecyclerView.ViewHolder {
    EaItemStartActivityBinding binding;

    public ActivityItemViewHolder(@NonNull View itemView) {
      super(itemView);
      binding = DataBindingUtil.bind(itemView);
    }

  }
}
