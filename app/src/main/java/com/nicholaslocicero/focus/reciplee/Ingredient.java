package com.nicholaslocicero.focus.reciplee;

public class Ingredient {

  private String mIngredient;
  private Float mAmount;

  public Ingredient() {
  }

  public String getIngredient() {
    return mIngredient;
  }

  public String getAmount() {
    return String.format("%.0f", mAmount);
  }

  public void setIngredient(String ingredient) {
    mIngredient = ingredient;
  }

  public void setAmount(Float amount) {
    mAmount = amount;
  }
}
