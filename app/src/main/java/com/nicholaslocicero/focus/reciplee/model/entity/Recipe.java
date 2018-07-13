package com.nicholaslocicero.focus.reciplee.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "recipes")
public class Recipe {

  @PrimaryKey
  private long id;

  private String title;
  private String directions;

  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }


  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }


  public String getDirections() {
    return directions;
  }
  public void setDirections(String directions) {
    this.directions = directions;
  }
}
