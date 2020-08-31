package com.lixueyang.exercise.models;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

/**
 * Created on 2020/8/24.
 */
public class User extends BaseObservable {
  public String firstName;
  public String lastName;

  public User(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @Bindable
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
    notifyPropertyChanged(BR.firstName);
  }

  @Bindable
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
    notifyPropertyChanged(BR.lastName);
  }
}
