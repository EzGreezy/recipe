package com.nicholaslocicero.focus.reciplee.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class User {

  @PrimaryKey(autoGenerate = true)
  private String username;
  @NonNull
  private String email;
  @NonNull
  private String password;
  @NonNull
  private String measurement;

}
