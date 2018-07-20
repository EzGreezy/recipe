package com.nicholaslocicero.focus.reciplee.model.dao;

public class Queries {

  public static final String INGREDIENTS_FROM_RECIPES_LOOK_UP =
      "SELECT ingredients.name AS ingredient, recipe_item.description AS recipe_item "
          + "FROM ingredients "
          + "LEFT JOIN ingredients_map ON ingredients.id = ingredients_map.ingredient_id "
          + "LEFT JOIN recipe_item ON ingredients_map.recipe_item_id = recipe_item.id "
          + "WHERE recipe_item.recipe_id = recipes.id AND recipes.title = :title";

  public static final String SHOPPING_LIST_ASSEMBLY =

      "SELECT "
          + "recipes.title, "
          + "COALESCE ( ingredients.name, ingredients2.name ) AS ingredient, "
          + "recipe_item.description AS description, "
          + "shopping_list.ingredient_item AS item "
          + "FROM shopping_list "
          + "LEFT JOIN ("
          +   "recipes "
          +   "INNER JOIN recipe_item ON recipe_item.recipe_id = recipes.id "
          +   "INNER JOIN ingredients_map ON ingredients_map.recipe_item_id = recipe_item.id "
          +   "INNER JOIN ingredients ON ingredients.id = ingredients_map.ingredient_id) "
          + "ON recipes.id = shopping_list.recipe_id "
          + "LEFT JOIN ingredients AS ingredients2 "
          + "ON ingredients2.id = shopping_list.ingredient_id "
          + "WHERE ingredients.id IS NOT NULL "
          +   "OR ingredients2.id IS NOT NULL ";
//      "SELECT "
//          + "ingredients.name AS ingredient, "
//          + "shopping_list.ingredient_item AS shopping_item, "
//          + "recipe_item.description AS recipe_ingredient "
//      + "FROM "
//          + "shopping_list "
//          + "LEFT JOIN ("
//            + "recipes "
//            + "INNER JOIN recipe_item "
//              + "ON recipe_item.recipe_id = recipes.id"
//          + ") "
//            + "ON recipes.id = shopping_list.recipe_id "
//          + "LEFT JOIN ("
//            + "ingredients "
//            + "INNER JOIN ingredients_map "
//              + "ON ingredients.id = ingredients_map.ingredient_id "
//            + "INNER JOIN recipe_item AS ri2 "
//              + "ON ri2.id = ingredients_map.recipe_item_id"
//          + ") "
//            + "ON shopping_list.ingredient_id = ingredients.id "
//              + "OR recipes.id = ri2.recipe_id";
//      "SELECT ingredients.name AS ingredient, "
//          + " shopping_list.ingredient_item AS shopping_item, "
//          + " recipe_list.description AS recipe_ingredient "
//          + "FROM shopping_list "
//          + "LEFT JOIN ( recipes "
//          + "  INNER JOIN "
//          + "  recipe_item ON recipe_item.recipe_id = recipes.id"
//          + ") AS recipe_list"
//          + "ON recipe_list.recipe_id = shopping_list.recipe_id "
//          + "LEFT JOIN ( ingredients "
//          + "  INNER JOIN ingredients_map ON ingredients.id = ingredients_map.ingredient_id "
//          + "  INNER JOIN recipe_item ON recipe_item.id = ingredients_map.recipe_item_id "
//          + ") AS ingredients_mapped "
//          + "  ON shopping_list.ingredient_id = ingredients_mapped.ingredient_id "
//          + "     OR recipe_list.recipe_id = ingredients_mapped.recipe_item_id ";

  // Stetho test query
  // SELECT ingredients.name AS ingredient, shopping_list.ingredient_item AS shopping_item, recipe_list.description AS recipe_ingredient FROM shopping_list LEFT JOIN ( recipes INNER JOIN recipe_item ON recipe_item.recipe_id = recipes.id ) AS recipe_list ON recipe_list.recipe_id = shopping_list.recipe_id LEFT JOIN (ingredients INNER JOIN ingredients_map ON ingredients.id = ingredients_map.ingredient_id INNER JOIN recipe_item ON recipe_item.id = ingredients_map.recipe_item_id ) AS ingredients_mapped ON shopping_list.ingredient_id = ingredients_mapped.ingredient_id OR recipe_list.id = ingredients_mapped.recipe_item_id
  //
  // SELECT ingredients.name AS ingredient,
  // shopping_list.ingredient_item AS shopping_item,
  // recipe_list.description AS recipe_ingredient
  // FROM shopping_list
  // LEFT JOIN ( recipes
  // INNER JOIN
  // recipe_item ON recipe_item.recipe_id = recipes.id
  // ) AS recipe_list
  // ON recipe_list.recipe_id = shopping_list.recipe_id
  // LEFT JOIN (ingredients
  // INNER JOIN ingredients_map ON ingredients.id = ingredients_map.ingredient_id
  // INNER JOIN recipe_item ON recipe_item.id = ingredients_map.recipe_item_id
  // ) AS ingredients_mapped
  // ON shopping_list.ingredient_id = ingredients_mapped.ingredient_id
  //    OR recipe_list.id = ingredients_mapped.recipe_item_id


}
