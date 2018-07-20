package com.nicholaslocicero.focus.reciplee.model.dao;

public class Queries {

  public static final String INGREDIENTS_FOR_RECIPES =
      "SELECT ingredients.name AS ingredient, recipe_item.description AS recipe_item "
          + "FROM ingredients "
          + "LEFT JOIN ingredients_map ON ingredients.id = ingredients_map.ingredient_id "
          + "LEFT JOIN recipe_item ON ingredients_map.recipe_item_id = recipe_item.id "
          + "WHERE recipe_item.recipe_id = recipes.id AND recipes.title = :title";

  public static final String SHOPPING_LIST_ASSEMBLY =
      "SELECT ingredients.name AS ingredient, "
          + " shopping_list.ingredient_item AS shopping_item, "
          + " recipe_item.description AS recipe_item "
          + "FROM shopping_list "
          + "LEFT JOIN ( recipes "
          + "  INNER JOIN "
          + "  recipe_item ON recipe_item.recipe_id = recipes.id"
          + ") AS recipe_list "
          + "ON recipe_list.recipe_id = shopping_list.recipe_id "
          + "LEFT JOIN ( ingredients "
          + "  INNER JOIN ingredients_mapped ON ingredients.id = ingredients_map.ingredient_id "
          + "  INNER JOIN ingredients_mapped ON recipe_item.id = ingredients_map.recipe_item_id "
          + ") AS ingredients_mapped "
          + "  ON shopping_list.ingredient_id = ingredients_mapped.ingredient_id "
          + "     OR recipe_list.recipe_item_id = ingredients_mapped.recipe_item_id ";
}
