package com.nicholaslocicero.focus.reciplee.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.nicholaslocicero.focus.reciplee.model.entity.Ingredient;
import com.nicholaslocicero.focus.reciplee.model.pojo.IngredientsMapRecipeItems;
import com.nicholaslocicero.focus.reciplee.model.pojo.ShoppingListAssembled;
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

//  @Query(Queries.INGREDIENTS_FOR_RECIPES)
//  List<IngredientsMapRecipeItems> selectIngredientsAndItems(String title);

//  @Query(Queries.SHOPPING_LIST_ASSEMBLY)
//  List<ShoppingListAssembled> selectIngredientsAndItems();
}
