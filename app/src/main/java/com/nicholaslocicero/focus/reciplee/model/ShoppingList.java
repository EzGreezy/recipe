package com.nicholaslocicero.focus.reciplee.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import java.util.Date;

@Entity(tableName = "shopping_list",
    primaryKeys = {"ingredient_id", "recipe_id"},
    foreignKeys = {
        @ForeignKey(entity = Recipes.class,
            parentColumns = "recipe_id",
            childColumns = "recipe_id"),
        @ForeignKey(entity = Recipes.class,
            parentColumns = "recipe_name",
            childColumns = "recipe_name"),
        @ForeignKey(entity = Recipes.class,
            parentColumns = "recipe_directions",
            childColumns = "recipe_directions"),
        @ForeignKey(entity = Recipes.class,
            parentColumns = "recipe_date",
            childColumns = "recipe_date"),
        @ForeignKey(entity = Ingredient.class,
            parentColumns = "ingredient_id",
            childColumns = "ingredient_id"),
        @ForeignKey(entity = Ingredient.class,
            parentColumns = "ingredient_name",
            childColumns = "ingredient_name"),
        @ForeignKey(entity = Ingredient.class,
            parentColumns = "ingredient_amount",
            childColumns = "ingredient_amount"),
        @ForeignKey(entity = Ingredient.class,
            parentColumns = "ingredient_measurement",
            childColumns = "ingredient_measurement")
    })
public class ShoppingList {

  private long recipe_id;
  private String recipe_name;
  private String recipe_directions;
  private Date recipe_date;

  private long ingredient_id;
  private String ingredient_name;
  private Float ingredient_amount;
  private String ingredient_measurement;
  private Boolean bought = false;

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

  public String getRecipe_directions() {
    return recipe_directions;
  }

  public void setRecipe_directions(String recipe_directions) {
    this.recipe_directions = recipe_directions;
  }

  public Date getRecipe_date() {
    return recipe_date;
  }

  public void setRecipe_date(Date recipe_date) {
    this.recipe_date = recipe_date;
  }

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

  public Boolean getBought() {
    return bought;
  }

  public void setBought(Boolean bought) {
    this.bought = bought;
  }
}
