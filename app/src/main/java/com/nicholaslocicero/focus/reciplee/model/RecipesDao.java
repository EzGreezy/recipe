package com.nicholaslocicero.focus.reciplee.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;

@Dao
public interface RecipesDao {

  @Insert
  long insert(Recipe recipe);

  @Query("SELECT * FROM recipes ORDER BY name DESC")
  List<Recipe> select();

  @Query("SELECT * FROM Ingredient WHERE recipe LIKE :recipe ORDER BY ingredient DESC")
  List<Ingredient> selectRecipe(String recipe);
}
