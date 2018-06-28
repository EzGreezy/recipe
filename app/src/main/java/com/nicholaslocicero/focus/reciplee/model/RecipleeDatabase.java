package com.nicholaslocicero.focus.reciplee.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import java.util.Date;

@Database(entities = {Ingredient.class, Recipe.class}, version = 1, exportSchema = true)
@TypeConverters(Converters.class)
public abstract class RecipleeDatabase extends RoomDatabase {

  public static final String DATABASE_NAME = "reciplee_db";

  private static RecipleeDatabase instance = null;

  public abstract IngredientsDao getIngredientsDao();

  public static RecipleeDatabase getInstance(Context context) {
    if (instance == null) {
      instance = Room.databaseBuilder(
          context.getApplicationContext(), RecipleeDatabase.class, DATABASE_NAME).build();
    }
    return instance;
  }

  public static void forgetInstance(Context context) {
    instance = null;
  }

  private static class Callback extends RoomDatabase.Callback {

    private Context context;

    private Callback(Context context) {
      this.context = context;
    }

    @Override
    public void onOpen(@NonNull SupportSQLiteDatabase db) {
      super.onOpen(db);
    }

    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);
      new PrepopulateTask().execute(context); // Call a task to pre-populate database.
    }
  }

  private static class PrepopulateTask extends AsyncTask<Context, Void, Void> {

    @Override
    protected Void doInBackground(Context... contexts) {
      RecipleeDatabase db = getInstance(contexts[0]);
      forgetInstance(contexts[0]);
      return null;
    }

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
