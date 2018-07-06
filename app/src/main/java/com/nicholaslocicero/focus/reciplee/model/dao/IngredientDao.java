package com.nicholaslocicero.focus.reciplee.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.nicholaslocicero.focus.reciplee.model.entity.Ingredient;
import java.util.List;

@Dao
public interface IngredientDao {

  @Insert
  long insert(Ingredient ingredient);

  @Insert
  List<Long> insert(Ingredient... ingredients);

  @Insert
  List<Long> insert(List<Ingredient> ingredients);

  @Query("SELECT * FROM ingredients")
  List<Ingredient> select();
}
