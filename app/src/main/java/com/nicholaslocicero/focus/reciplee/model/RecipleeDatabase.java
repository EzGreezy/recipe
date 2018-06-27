package com.nicholaslocicero.focus.reciplee.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import java.util.Date;

@Database(entities = {Ingredient.class, Recipe.class}, version = 1, exportSchema = true)
@TypeConverters(Converters.class)
public abstract class RecipleeDatabase extends RoomDatabase {

  public static final String DATABASE_NAME = "reciplee_db";

  private static RecipleeDatabase instance = null;

  public static RecipleeDatabase getInstance(Context context) {
    if(instance == null) {
      instance = Room.databaseBuilder(
          context.getApplicationContext(), RecipleeDatabase.class, DATABASE_NAME).build();
    }
    return instance;
  }

  public abstract IngredientsDao getIngredientDao();

  public void forgetInstance(Context context) {
    instance = null;
  }
}

class Converters {

  @TypeConverter
  public static Date dateFromTimestamp(Long timestamp) {
    return (timestamp != null) ? new Date(timestamp) : null;
  }

  @TypeConverter
  public static Long timestampFromDate(Date date) {
    return (date != null) ? date.getTime() : null;
  }
}
