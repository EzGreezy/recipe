package com.nicholaslocicero.focus.reciplee.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;

@Dao
public interface IngredientsDao {

  @Insert
  long insert(Ingredient ingredient);

  @Query("SELECT * FROM ingredients ORDER BY ingredient_id DESC")
  List<Ingredient> select();

  @Query("SELECT * FROM ingredients, recipes WHERE recipes.name LIKE :recipe ORDER BY ingredient_id DESC")
  List<Ingredient> selectRecipe(String recipe);

}
