package com.nicholaslocicero.focus.reciplee.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(
  tableName = "shopping_list",
  foreignKeys = {
    @ForeignKey(
      entity = Ingredient.class,
      parentColumns = "id", childColumns = "ingredient_id"
    ),
    @ForeignKey(
      entity = Recipe.class,
      parentColumns = "id", childColumns = "recipe_id"
    )
  },
    indices = {
        @Index(value = {"id", "ingredient_id", "recipe_id"}, unique = true)
    }
)
public class ShoppingItem {
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "id", index = true)
  private long id;

  @ColumnInfo(name = "ingredient_id", index = true)
  private Long ingredient_id;
  @ColumnInfo(name = "ingredient_item")
  private String ingredient_item;
  @ColumnInfo(name = "recipe_id", index = true)
  private Long recipe_id;
  private int position;

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

  public String getIngredient_item() {
    return ingredient_item;
  }

  public void setIngredient_item(String ingredient_item) {
    this.ingredient_item = ingredient_item;
  }

  public long getRecipe_id() {
    return recipe_id;
  }

  public void setRecipe_id(long recipe_id) {
    this.recipe_id = recipe_id;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }
}
