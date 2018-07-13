package com.nicholaslocicero.focus.reciplee.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "ingredients_map",
    foreignKeys = {
        @ForeignKey(
            entity = RecipeItem.class,
            parentColumns = "id", childColumns = "recipe_item_id"
        ),
        @ForeignKey(
            entity = Ingredient.class,
            parentColumns = "id", childColumns = "ingredient_id"
        )
    },
    indices = {
        @Index(value = {"id", "recipe_id", "ingredient_id"}, unique = true)
    }
)
public class IngredientMap {
  @PrimaryKey
  private long id;
  private long ingredientId;
  private long recipeItemId;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getIngredientId() {
    return ingredientId;
  }

  public void setIngredientId(long ingredientId) {
    this.ingredientId = ingredientId;
  }

  public long getRecipeItemId() {
    return recipeItemId;
  }

  public void setRecipeItemId(long recipeItemId) {
    this.recipeItemId = recipeItemId;
  }
}
