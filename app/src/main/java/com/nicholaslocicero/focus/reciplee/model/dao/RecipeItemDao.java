package com.nicholaslocicero.focus.reciplee.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.nicholaslocicero.focus.reciplee.model.entity.RecipeItem;
import java.util.List;

@Dao
public interface RecipeItemDao {

  @Insert
  long insert(RecipeItem recipeItem);

  @Insert
  List<Long> insert(RecipeItem... recipeItems);

  @Insert
  List<Long> insert(List<RecipeItem> recipeItems);

  @Query("SELECT MAX(id) FROM recipe_item")
  Long selectMax();
}
