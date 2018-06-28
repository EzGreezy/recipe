package com.nicholaslocicero.focus.reciplee.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;
import java.util.Date;

@Entity(
  tableName = "recipes",
  primaryKeys = {"recipe_id"}
)
public class Recipes {

  // Just have recipe_name be primary key and leave out id ??
  private long recipe_id;
  private String recipe_name;
  private String recipe_directions;
  private Date recipe_date;

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
}
