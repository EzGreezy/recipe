package com.nicholaslocicero.focus.reciplee;

import android.content.Context;
import com.nicholaslocicero.focus.reciplee.model.entity.Ingredient;
import java.util.ArrayList;
import java.util.List;

public class KitchenIngredients {

  private static KitchenIngredients sKitchenIngredients;
  private List<Ingredient> mIngredients;

  public static KitchenIngredients get(Context context) {
    if (sKitchenIngredients == null) {
      sKitchenIngredients = new KitchenIngredients(context);
    }
    return sKitchenIngredients;
  }

  private KitchenIngredients(Context context) {
    mIngredients = new ArrayList<>();
  }

  public List<Ingredient> getIngredients() { return mIngredients;}

  public void addIngredient(Ingredient ingredient) {
    mIngredients.add(0, ingredient);
  }
}
