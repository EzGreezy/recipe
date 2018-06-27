package com.nicholaslocicero.focus.reciplee.model;

import static android.arch.persistence.room.ColumnInfo.NOCASE;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "recipes")
public class Recipe {

  @PrimaryKey(autoGenerate = true)
  private long recipeId;

  @NonNull
  @ColumnInfo(name="name", collate = NOCASE)
  private String name;

  // TODO figure out how to list steps. Another table with number as steps? A string with separators?
  private String steps;

  public long getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(long recipeId) {
    this.recipeId = recipeId;
  }

  @NonNull
  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }

  public String getSteps() {
    return steps;
  }

  public void setSteps(String steps) {
    this.steps = steps;
  }
}
