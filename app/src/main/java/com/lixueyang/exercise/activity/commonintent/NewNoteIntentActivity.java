package com.lixueyang.exercise.activity.commonintent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lixueyang.exercise.databinding.ActivityNewNoteIntentBinding;

/**
 * 创建笔记intent
 * 注：这个需要使用 google play服务，否则的话无法使用NoteIntents
 */
public class NewNoteIntentActivity extends AppCompatActivity {

  private ActivityNewNoteIntentBinding binding;

  public static void startNewNoteIntentActivity(Activity activity) {
    Intent intent = new Intent(activity, NewNoteIntentActivity.class);
    activity.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityNewNoteIntentBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    binding.btnCreateNewNote.setOnClickListener(view -> {
//      Intent intent = new Intent(NoteIntents.ACTION_CREATE_NOTE)
//          .putExtra(NoteIntents.EXTRA_NAME, binding.etNewNoteTitle.getText())
//          .putExtra(NoteIntents.EXTRA_TEXT, binding.etNewNoteContent.getText());
//      if (intent.resolveActivity(getPackageManager()) != null) {
//        startActivity(intent);
//      }
    });
  }
}