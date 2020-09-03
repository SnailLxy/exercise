package com.lixueyang.exercise.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created on 2020/8/25.
 */
public class UserModel extends ViewModel {
  public LiveData<String> username = new MutableLiveData<>();

  public UserModel(LiveData<String> username) {
    this.username = username;
  }

  public LiveData<String> getUsername() {
    return username;
  }
}
