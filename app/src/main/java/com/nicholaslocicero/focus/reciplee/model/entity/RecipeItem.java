package com.nicholaslocicero.focus.reciplee.model.entity;

import android.arch.persistence.room.ColumnInfo;
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
        @Index(value = {"id", "ingredient_id", "recipe_id"}, unique = true)
    }
)
public class RecipeItem {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "id", index = true)
  private long id;

  @ColumnInfo(name = "ingredient_id", index = true)
  private long ingredient_id;
  @ColumnInfo(name = "recipe_id", index = true)
  private long recipe_id;

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

  public Float getQuantity() {
    return quantity;
  }

  public void setQuantity(Float quantity) {
    this.quantity = quantity;
  }
}
