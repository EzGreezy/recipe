package com.nicholaslocicero.focus.reciplee.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.nicholaslocicero.focus.reciplee.model.entity.Ingredient;
import com.nicholaslocicero.focus.reciplee.model.pojo.IngredientsMapRecipeItems;
import java.util.List;

@Dao
public interface IngredientDao {

  @Insert()
  long insert(Ingredient ingredient);

  @Insert
  List<Long> insert(Ingredient... ingredients);

  @Insert
  List<Long> insert(List<Ingredient> ingredients);

  @Query("SELECT MAX(id) FROM ingredients")
  Long selectMax();

  @Query("SELECT * FROM ingredients")
  List<Ingredient> select();

  @Query("SELECT id FROM ingredients WHERE name LIKE :text")
  Long getIngredientIdByName(String text);

//  @Query("SELECT CASE WHEN EXISTS (SELECT * FROM ingredients WHERE ingredients.name = :text) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END")
//  Boolean isIngredient(String text);

//  @Query(Queries.INGREDIENTS_FROM_RECIPES_LOOK_UP)
//  List<IngredientsMapRecipeItems> selectIngredientsAndItems(String title);

//  @Query(Queries.SHOPPING_LIST_ASSEMBLY)
//  List<ShoppingListAssembled> selectIngredientsAndItems();
}
