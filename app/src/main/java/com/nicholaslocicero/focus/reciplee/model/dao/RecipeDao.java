package com.nicholaslocicero.focus.reciplee.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.nicholaslocicero.focus.reciplee.model.entity.Ingredient;
import com.nicholaslocicero.focus.reciplee.model.entity.Recipe;
import java.util.List;

@Dao
public interface RecipeDao {

  @Insert
  long insert(Recipe recipe);

  @Insert
  List<Long> insert(Recipe... recipes);

  @Insert
  List<Long> insert(List<Recipe> recipes);

  @Query("SELECT title FROM recipes")
  List<String> selectRecipeTitles();

  @Query("SELECT id FROM recipes")
  List<Long> selectRecipeIds();
}
