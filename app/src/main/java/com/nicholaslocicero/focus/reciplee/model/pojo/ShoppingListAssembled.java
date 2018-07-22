package com.nicholaslocicero.focus.reciplee.model.pojo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import java.util.List;

public class ShoppingListAssembled {

  private String title;
  private String ingredient;
  private String item;
  private String description;
  private String remove;

  public String getIngredient() {
    return ingredient;
  }

  public void setIngredient(String ingredient) {
    this.ingredient = ingredient;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getItem() {
    return item;
  }

  public void setItem(String item) {
    this.item = item;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getRemove() {
    return remove;
  }

  public void setRemove(String remove) {
    this.remove = remove;
  }
}
