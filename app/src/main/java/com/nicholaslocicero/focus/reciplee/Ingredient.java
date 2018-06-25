package com.nicholaslocicero.focus.reciplee;

public class Ingredient {

  private String mIngredient;
  private Float mAmount;

  public Ingredient(String ingredient, Float amount) {
    mIngredient = ingredient;
    mAmount = amount;
  }

  public String getIngredient() {
    return mIngredient;
  }

  public String getAmount() {
    return String.format("%.0f", mAmount);
  }
}
