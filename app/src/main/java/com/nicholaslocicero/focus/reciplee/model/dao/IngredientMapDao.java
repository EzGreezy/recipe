package com.nicholaslocicero.focus.reciplee.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import com.nicholaslocicero.focus.reciplee.model.entity.IngredientMap;
import java.util.List;

@Dao
public interface IngredientMapDao {

  @Insert
  long insert(IngredientMap ingredientMap);

  @Insert
  List<Long> insert(IngredientMap... ingredients);

  @Insert
  List<Long> insert(List<IngredientMap> ingredients);

}
