package com.nicholaslocicero.focus.reciplee.model.dao;

public class Queries {

  public static final String SHOPPING_ITEMS =
      "SELECT "
          + "  ingredient.name, "
          + "  shopping_item.quantity, "
          + "  recipe_item.quantity "
          + "FROM "
          + "  ShoppingItem AS shopping_item "
          + "  LEFT JOIN ("
          + "    Recipe as recipe "
          + "    INNER JOIN "
          + "      RecipeItem AS recipe_item "
          + "      ON recipe_item.recipe_id = recipe.recipe_id "
          + ") AS recipe_list "
          + "  ON recipe_list.recipe_id = shopping_item.recipe_id "
          + "  LEFT JOIN Ingredient AS ingredient "
          + "    ON ingredient.id = recipe_item.ingredient_id "
          + "      OR ingredient.id = shopping.ingredient_id "
          + "ORDER BY "
          + "  shopping_item.position"
          + "GROUP BY ingredient.name";
}
