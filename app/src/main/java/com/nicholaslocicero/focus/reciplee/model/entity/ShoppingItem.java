package com.nicholaslocicero.focus.reciplee.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(
  tableName = "grocery_list",
  foreignKeys = {
    @ForeignKey(
      entity = Ingredient.class,
      parentColumns = "id", childColumns = "ingredient_id"
    ),
    @ForeignKey(
      entity = Recipe.class,
      parentColumns = "id", childColumns = "recipe_id"
    ),
    @ForeignKey(
      entity = RecipeItem.class,
      parentColumns = "id", childColumns = "recipe_item_id"
    )
  }
)
public class ShoppingItem {
  @PrimaryKey(autoGenerate = true)
  private long id;

  private long ingredient_id;
  private long recipe_id;
  private long recipe_item_id;
  private int position;

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  @NonNull
  private Float quantity;

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

  public long getRecipe_item_id() {
    return recipe_item_id;
  }

  public void setRecipe_item_id(long recipe_item_id) {
    this.recipe_item_id = recipe_item_id;
  }

  @NonNull
  public Float getQuantity() {
    return quantity;
  }

  public void setQuantity(@NonNull Float quantity) {
    this.quantity = quantity;
  }
}
