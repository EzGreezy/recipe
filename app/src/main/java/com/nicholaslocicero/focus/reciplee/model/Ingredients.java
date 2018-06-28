package com.nicholaslocicero.focus.reciplee.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(
    tableName = "ingredients",
    foreignKeys = {
        @ForeignKey(entity = Recipes.class,
            parentColumns = "recipe_id",
            childColumns = "recipe_id"),
        @ForeignKey(entity = Recipes.class,
          parentColumns = "recipe_name",
          childColumns = "recipe_name")
    }
)
public class Ingredients {
  private long ingredient_id;
  private String ingredient_name;
  private Float ingredient_amount;
  private String ingredient_measurement;
  private long recipe_id;
  private String recipe_name;

  public long getIngredient_id() {
    return ingredient_id;
  }

  public void setIngredient_id(long ingredient_id) {
    this.ingredient_id = ingredient_id;
  }

  public String getIngredient_name() {
    return ingredient_name;
  }

  public void setIngredient_name(String ingredient_name) {
    this.ingredient_name = ingredient_name;
  }

  public Float getIngredient_amount() {
    return ingredient_amount;
  }

  public void setIngredient_amount(Float ingredient_amount) {
    this.ingredient_amount = ingredient_amount;
  }

  public String getIngredient_measurement() {
    return ingredient_measurement;
  }

  public void setIngredient_measurement(String ingredient_measurement) {
    this.ingredient_measurement = ingredient_measurement;
  }

  public long getRecipe_id() {
    return recipe_id;
  }

  public void setRecipe_id(long recipe_id) {
    this.recipe_id = recipe_id;
  }

  public String getRecipe_name() {
    return recipe_name;
  }

  public void setRecipe_name(String recipe_name) {
    this.recipe_name = recipe_name;
  }
}
