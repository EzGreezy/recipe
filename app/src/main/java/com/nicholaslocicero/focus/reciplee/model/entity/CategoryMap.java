package com.nicholaslocicero.focus.reciplee.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "categories_map",
    foreignKeys = {
    @ForeignKey(
        entity = Category.class,
        parentColumns = "id", childColumns = "recipe_item_id"
    ),
    @ForeignKey(
        entity = Recipe.class,
        parentColumns = "id", childColumns = "ingredient_id"
    )
},
    indices = {
        @Index(value = {"id", "recipe_id", "ingredient_id"}, unique = true)
    }

    )
public class CategoryMap {
  @PrimaryKey
  private long id;
  private long recipeId;
  private long categoryId;

  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }

  public long getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(long recipId) {
    this.recipeId = recipeId;
  }

  public long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(long categoryId) {
    this.categoryId = categoryId;
  }
}
