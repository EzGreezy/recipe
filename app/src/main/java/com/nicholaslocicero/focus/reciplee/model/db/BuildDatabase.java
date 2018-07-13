package com.nicholaslocicero.focus.reciplee.model.db;


import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class BuildDatabase extends SQLiteAssetHelper {

  private static final String DATABASE_NAME = "northwind.db";
  public static final int DATABASE_VERSION = 1;

  public BuildDatabase(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }
}
