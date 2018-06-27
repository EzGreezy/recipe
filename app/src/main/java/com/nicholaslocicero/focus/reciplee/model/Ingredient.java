package com.nicholaslocicero.focus.reciplee.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "ingredients")
public class Ingredient {

  @PrimaryKey(autoGenerate = true)
  private long ingredientId;
  @NonNull
  private String ingredient;
  // now can be null
  private Float amount;
  private String measurement;
  // TODO make long reference to other id in table
  @ColumnInfo(name="recipe")
  private String recipe;

  public long getId() { return ingredientId; }
  @NonNull
  public String getIngredient() {
    return ingredient;
  }
  @NonNull
  public String getAmount() {
    return String.format("%.0f", amount);
  }
  @NonNull
  public String getMeasurement() {return measurement; }

  public void setIngredientId(long ingredientId) {
    this.ingredientId = ingredientId;
  }

  public void setIngredient(@NonNull String ingredient) {
    this.ingredient = ingredient;
  }

  public void setAmount(@NonNull float amount) {
    this.amount = amount;
  }

  public void setMeasurement(@NonNull String measurement) {
    this.measurement = measurement;
  }
}
