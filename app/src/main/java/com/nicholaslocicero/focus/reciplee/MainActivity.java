package com.nicholaslocicero.focus.reciplee;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.TextView;
import com.nicholaslocicero.focus.reciplee.model.db.Reciplee;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener{

  Toolbar toolbar;
  String[] funNavHeaderIcons = {"\uD83E\uDD57", "\uD83C\uDF71", "🍜", "🍿", "🥫", "🍚", "🍛", "🍜", "🍝",
                                "🍠", "🍣", "🍤", "🍥", "🍦", "🥧", "☕"};

  Random rng = new Random();

  @SuppressLint("StaticFieldLeak")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
//    setNavigationViewListener();
    final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
      @Override
      public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.imageView);
        navUsername.setText(funNavHeaderIcons[rng.nextInt(funNavHeaderIcons.length)]);
      }
    };
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    GroceryListFragment shoppingList = new GroceryListFragment();
    FragmentManager manager = getSupportFragmentManager();
    manager.beginTransaction()
        .replace(R.id.main_layout, shoppingList)
        .commit();

    new AsyncTask<Context, Void, Void>() {
      @Override
      protected Void doInBackground(Context... contexts) {
        // Replace Attendance and getStudentDao with the relevant class & method names for your project.
        Reciplee.getInstance(contexts[0]).getIngredientDao().select();
        return null;
      }
    }.execute(this);
    
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setTitle("Shopping List");
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }


  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_shopping_list) {
      toolbar = (Toolbar) findViewById(R.id.toolbar);
      toolbar.setTitle("Shopping List");

      GroceryListFragment shoppingList = new GroceryListFragment();
      FragmentManager manager = getSupportFragmentManager();
      manager.beginTransaction()
          .replace(R.id.main_layout, shoppingList)
          .commit();

    } else if (id == R.id.nav_meal_planner) {
      toolbar = (Toolbar) findViewById(R.id.toolbar);
      toolbar.setTitle("Meal Planner");

      MealPlannerFragment mealPlannerFragment = new MealPlannerFragment();
      FragmentManager manager = getSupportFragmentManager();
      manager.beginTransaction()
          .replace(R.id.main_layout, mealPlannerFragment)
          .commit();

    }
//
//    } else if (id == R.id.nav_slideshow) {
//
//    } else if (id == R.id.nav_manage) {
//
//    } else if (id == R.id.nav_share) {
//
//    } else if (id == R.id.nav_send) {
//
//    }


    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }
}
