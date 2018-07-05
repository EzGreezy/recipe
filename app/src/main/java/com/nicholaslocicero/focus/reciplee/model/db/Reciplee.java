package com.nicholaslocicero.focus.reciplee.model.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import java.util.Date;

public abstract class Reciplee extends RoomDatabase {

  public static final String DATABASE_NAME = "reciplee_db";

  private static Reciplee instance = null;

  // DAOs here

  public static Reciplee getInstance(Context context) {
    if (instance == null) {
      instance = Room.databaseBuilder(
          context.getApplicationContext(), Reciplee.class, DATABASE_NAME).build();
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
      new Reciplee.PrepopulateTask().execute(context); // Call a task to pre-populate database.
    }
  }

  private static class PrepopulateTask extends AsyncTask<Context, Void, Void> {

    @Override
    protected Void doInBackground(Context... contexts) {
      Reciplee db = getInstance(contexts[0]);
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