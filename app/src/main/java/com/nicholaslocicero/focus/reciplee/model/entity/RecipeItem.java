package com.nicholaslocicero.focus.reciplee.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(
    foreignKeys = {
        @ForeignKey(
            entity = Recipe.class,
            parentColumns = "recipe_id", childColumns = "recipe_id"
        ),
        @ForeignKey(
            entity = Ingredient.class,
            parentColumns = "id", childColumns = "ingredient_id"
        )
    },
    indices = {
        @Index(value = {"student_id", "start_date", "duration"}, unique = true)
    }
)
public class RecipeItem {

  @PrimaryKey(autoGenerate = true)
  private long id;

  private long ingredient_id;
  private long recipe_id;

  @NonNull
  private Float quantity;
  private String measurement;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getIngredient_id() {
    return ingredient_id;
  }

  public void setIngredient_id(long ingredient_id) {
    this.ingredient_id = ingredient_id;
  }

  public long getRecipe_id() {
    return recipe_id;
  }

  public void setRecipe_id(long recipe_id) {
    this.recipe_id = recipe_id;
  }

  public float getQuantity() {
    return quantity;
  }

  public void setQuantity(float quantity) {
    this.quantity = quantity;
  }

  public String getMeasurement() {
    return measurement;
  }

  public void setMeasurement(String measurement) {
    this.measurement = measurement;
  }
}
