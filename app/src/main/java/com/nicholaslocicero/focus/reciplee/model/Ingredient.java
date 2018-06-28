package com.nicholaslocicero.focus.reciplee.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "ingredients",
  primaryKeys = {"ingredient_id", "recipe_id"})
public class Ingredient {
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "ingredient_id")
  private long ingredientId;

  @NonNull
  private String name;
  private String amount;

  public long getIngredientId() {
    return ingredientId;
  }

  public String getRecipe() {
    return recipe;
  }

  private String measurement;

  public void setRecipe(String recipe) {
    this.recipe = recipe;
  }

  // TODO make long reference to other id in table
  @ColumnInfo(name="recipe", index = true)
  private String recipe;

  public long getId() { return ingredientId; }
  @NonNull
  public String getName() {
    return name;
  }
  @NonNull
  public String getAmount() {
    return amount;
  }
  @NonNull
  public String getMeasurement() {return measurement; }

  public void setIngredientId(long ingredientId) {
    this.ingredientId = ingredientId;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }

  public void setAmount(@NonNull String amount) {
    this.amount = amount;
  }

  public void setMeasurement(@NonNull String measurement) {
    this.measurement = measurement;
  }
}
