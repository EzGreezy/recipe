package com.nicholaslocicero.focus.reciplee;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.nicholaslocicero.focus.reciplee.model.db.Reciplee;
import com.nicholaslocicero.focus.reciplee.model.entity.Recipe;
import com.nicholaslocicero.focus.reciplee.model.entity.RecipeItem;
import com.nicholaslocicero.focus.reciplee.model.entity.ShoppingItem;
import com.nicholaslocicero.focus.reciplee.model.pojo.ShoppingListAssembled;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.w3c.dom.Text;


public class MealPlannerFragment extends Fragment {

  private ListView mealPlannerListView;
  private MealAdapter mealAdapter;
  private int position;
  private Set<ShoppingListAssembled> meals;
  private List<Recipe> mealObject;
  private List<String> mealTitles;

  public MealPlannerFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_meal_planner, container, false);

    meals = new HashSet<>();
    mealObject = new ArrayList<>();
    mealTitles = new ArrayList<>();
    mealAdapter = new MealAdapter();
    mealPlannerListView = (ListView) view.findViewById(R.id.meal_planner_list_view);
    mealPlannerListView.setAdapter(mealAdapter);
    updateUI();

    mealPlannerListView.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.remove_ingredient_dialog, null);
        Button delete = (Button) dialogView.findViewById(R.id.delete_ingredient);
        Button back = (Button) dialogView.findViewById(R.id.dont_delete_ingredient);
        TextView title = (TextView) dialogView.findViewById(R.id.ingredient_to_delete);
        title.setText(mealTitles.get(position));
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.show();
        back.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
            dialog.dismiss();
          }
        });
        delete.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
            new RemoveRecipeByTitle().execute(mealTitles.get(position));
            dialog.dismiss();
          }
        });
      }
    });

    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    updateUI();
  }

  private void updateUI() {
    new UpdateRecipes().execute();
  }

  private class MealAdapter extends BaseAdapter {

    @Override
    public int getCount() {
      return mealTitles.size();
    }

    @Override
    public Object getItem(int position) {
      return null;
    }

    @Override
    public long getItemId(int position) {
      return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      convertView = getLayoutInflater().inflate(R.layout.meal_planner_item, null);
      TextView title = convertView.findViewById(R.id.meal_title);
      TextView directions = convertView.findViewById(R.id.meal_directions);
      TextView ingredientsTitle = convertView.findViewById(R.id.ingredients_title);
      TextView ingredients = convertView.findViewById(R.id.meal_ingredients);

      title.setText(Html.fromHtml("<h2><font color = '#FF4081'>" + mealTitles.get(position) + "</font></h2>"));
      for (Recipe recipe : mealObject) {
        if (recipe.getTitle().equals(mealTitles.get(position))) {
          directions.setText(Html.fromHtml(recipe.getDirections()));
        }
      }
      String ingredientsString = "";

      ingredientsTitle.setText(Html.fromHtml("<h3>Ingredients</h3>"));

      for (ShoppingListAssembled shoppingItem : meals) {
        if (shoppingItem.getTitle().equals(mealTitles.get(position)) &&
            // make sure we aren't adding ingredients from a duplicate recipe
            !ingredientsString.contains(shoppingItem.getDescription())) {
          ingredientsString += "<p>" + shoppingItem.getDescription() + "</p>";
        }
      }

      ingredients.setText(Html.fromHtml(ingredientsString.toString()));
      return convertView;
    }
  }

  private class UpdateRecipes extends AsyncTask<Void, Void, List<Recipe>> {

    @Override
    protected List<Recipe> doInBackground(Void... voids) {
      return Reciplee.getInstance(getContext()).getRecipeDao().selectRecipesInShoppingList();
    }

    @Override
    protected void onPostExecute(List<Recipe> recipes) {
      mealObject.clear();
      mealObject.addAll(recipes);
      mealTitles.clear();
      for (Recipe recipe : mealObject) {
        mealTitles.add(recipe.getTitle());
      }
      Collections.sort(mealTitles);
      new ShoppingItemQuery().execute();
    }
  }

  private class RemoveRecipeByTitle extends AsyncTask<String, Void, Recipe> {

    @Override
    protected Recipe doInBackground(String... strings) {
      return Reciplee.getInstance(getContext()).getRecipeDao().getRecipeByTitle(strings[0]);
    }

    @Override
    protected void onPostExecute(Recipe recipe) {
      Long id = recipe.getId();
      new GetShoppingItemToDelete().execute(id);
    }
  }

  private class GetShoppingItemToDelete extends AsyncTask<Long, Void, ShoppingItem> {

    @Override
    protected ShoppingItem doInBackground(Long... longs) {
      return Reciplee.getInstance(getContext()).getShoppingItemDao().selectById(longs[0]);
    }

    @Override
    protected void onPostExecute(ShoppingItem shoppingItem) {
      new DeleteRecipeFromShoppingItems().execute(shoppingItem);
    }
  }

  private class DeleteRecipeFromShoppingItems extends AsyncTask<ShoppingItem, Void, Void> {

    @Override
    protected Void doInBackground(ShoppingItem... shoppingItems) {
      Reciplee.getInstance(getContext()).getShoppingItemDao().delete(shoppingItems[0]);
      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      updateUI();
    }
  }

  private class ShoppingItemQuery extends AsyncTask<Void, Void, List<ShoppingListAssembled>> {

    @Override
    protected List<ShoppingListAssembled> doInBackground(Void... voids) {
      return Reciplee.getInstance(getContext()).getShoppingItemDao().assembleShoppingList();
    }
    @Override
    protected void onPostExecute(List<ShoppingListAssembled> shoppingItems) {
      meals.clear();
      for (ShoppingListAssembled item : shoppingItems) {
        if (mealTitles.contains(item.getTitle())) {
          meals.add(item);
        }
      }
      mealAdapter.notifyDataSetChanged();
    }
  }

}
