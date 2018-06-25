package com.nicholaslocicero.focus.reciplee;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KitchenIngredients {

  private static KitchenIngredients sKitchenIngredients;
  private List<Ingredient> mIngredients;
  private final String[] randomFoods = {"pancetta",
      "grapefruits",
      "green onions",
      "chutney",
      "cottage cheese",
      "custard",
      "ricotta cheese",
      "walnuts",
      "bean sauce",
      "tonic water",
      "mushrooms",
      "okra",
      "limes",
      "venison",
      "wild rice",
      "jicama",
      "strawberries",
      "pecans",
      "hazelnuts",
      "barbecue sauce"
  };

  Random rng = new Random();

  public static KitchenIngredients get(Context context) {
    if (sKitchenIngredients == null) {
      sKitchenIngredients = new KitchenIngredients(context);
    }
    return sKitchenIngredients;
  }

  private KitchenIngredients(Context context) {
    mIngredients = new ArrayList<>();
    for (String food : randomFoods) {
      Ingredient ingredient = new Ingredient(food, rng.nextFloat() * 10);
      mIngredients.add(ingredient);
    }
  }

  public List<Ingredient> getIngredients() { return mIngredients;}
}
