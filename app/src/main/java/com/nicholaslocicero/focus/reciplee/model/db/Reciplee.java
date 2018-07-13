package com.nicholaslocicero.focus.reciplee.model.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.gson.JsonArray;
import com.nicholaslocicero.focus.reciplee.R;
import com.nicholaslocicero.focus.reciplee.model.dao.IngredientDao;
import com.nicholaslocicero.focus.reciplee.model.dao.RecipeDao;
import com.nicholaslocicero.focus.reciplee.model.dao.RecipeItemDao;
import com.nicholaslocicero.focus.reciplee.model.dao.ShoppingItemDao;
import com.nicholaslocicero.focus.reciplee.model.entity.Ingredient;
import com.nicholaslocicero.focus.reciplee.model.entity.Recipe;
import com.nicholaslocicero.focus.reciplee.model.entity.RecipeItem;
import com.nicholaslocicero.focus.reciplee.model.entity.ShoppingItem;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.stream.JsonReader;

@Database(entities = {Ingredient.class, Recipe.class, RecipeItem.class, ShoppingItem.class}, version = 1, exportSchema = true)
public abstract class Reciplee extends RoomDatabase {

  public static final String DATABASE_NAME = "reciplee_db";

  private static Reciplee instance = null;

  public abstract IngredientDao getIngredientDao();

  public abstract RecipeDao getRecipeDao();

  public abstract RecipeItemDao getRecipeItemDao();

  public abstract ShoppingItemDao getShoppingItemDao();

  public static Reciplee getInstance(Context context) {
    if (instance == null) {
      instance = Room.databaseBuilder(context.getApplicationContext(), Reciplee.class, DATABASE_NAME)
          .addCallback(new Callback(context))
          .build();
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

  private static class PrepopulateTask extends AsyncTask<Context, Void, Void>  {

    @Override
    protected Void doInBackground(Context... contexts) {

      Reciplee db = getInstance(contexts[0]);
      String directions = "";
      String title = "";
      Recipe recipe = new Recipe();

      try {

        JsonReader reader = new JsonReader(new InputStreamReader(contexts[0].getResources().openRawResource(R.raw.recipe)));
        reader.beginArray();

        while (reader.hasNext()) {
          reader.beginObject();

          while (reader.hasNext()) {
            String name = reader.nextName();

            if (name.equals("directions")) {
              directions = reader.nextString();
              recipe.setDirections(directions);

            } else if (name.equals("title")) {
              title = reader.nextString();
              recipe.setName(title);
              db.getRecipeDao().insert(recipe);
            } else {
              reader.skipValue(); //avoid some unhandle events
            }

          }

          reader.endObject();

        }

        reader.endArray();
        reader.close();

      } catch (Exception e) {
        Log.d("EXCEPTION", "no", e);
      }
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