package com.nicholaslocicero.focus.reciplee.model.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.stream.JsonWriter;
import java.util.List;

public class GetRecipes {

  @SerializedName("title")
  @Expose
  private String title;
  @SerializedName("directions")
  @Expose
  private List<String> directions = null;
  @SerializedName("ingredients")
  @Expose
  private List<Integer> ingredients = null;
  @SerializedName("categories")
  @Expose
  private List<Integer> categories = null;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<String> getDirections() {
    return directions;
  }

  public void setDirections(List<String> directions) {
    this.directions = directions;
  }

  public List<Integer> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<Integer> ingredients) {
    this.ingredients = ingredients;
  }

  public List<Integer> getCategories() {
    return categories;
  }

  public void setCategories(List<Integer> categories) {
    this.categories = categories;
  }

}
