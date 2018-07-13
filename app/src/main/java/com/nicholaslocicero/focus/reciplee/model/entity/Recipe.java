package com.nicholaslocicero.focus.reciplee.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "recipes")
public class Recipe {

  @PrimaryKey
  private long id;

  private String name;
  private String directions;

  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }


  public String getDirections() {
    return directions;
  }
  public void setDirections(String directions) {
    this.directions = directions;
  }
}
