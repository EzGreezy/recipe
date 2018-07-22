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
import com.nicholaslocicero.focus.reciplee.model.entity.ShoppingItem;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass. Use the {@link MealPlannerFragment#newInstance} factory
 * method to create an instance of this fragment.
 */
public class MealPlannerFragment extends Fragment {

  private ListView mealPlannerRecyclerView;
  private MealAdapter mealAdapter;
  private int position;
  private List<Recipe> meals;

  public MealPlannerFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_meal_planner, container, false);

    meals = new ArrayList<>();
    mealAdapter = new MealAdapter();
    mealPlannerRecyclerView = (ListView) view.findViewById(R.id.meal_planner_list_view);
    mealPlannerRecyclerView.setAdapter(mealAdapter);
    updateUI();

    mealPlannerRecyclerView.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.remove_ingredient_dialog, null);
        Button delete = (Button) dialogView.findViewById(R.id.delete_ingredient);
        Button back = (Button) dialogView.findViewById(R.id.dont_delete_ingredient);
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
            new RemoveRecipeByTitle().execute(meals.get(position).getTitle());
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


//  private class MealHolder extends RecyclerView.ViewHolder {
//    private Recipe recipe;
//    private TextView mealTitle;
//    private TextView mealDirections;
//
//    public MealHolder(LayoutInflater inflater, ViewGroup parent) {
//      super(inflater.inflate(R.layout.meal_planner_item, parent, false));
//
//      mealTitle = (TextView) itemView.findViewById(R.id.meal_title);
//      mealDirections = (TextView) itemView.findViewById(R.id.meal_directions);
//    }
//
//    public void bind(Recipe recipe) {
//      this.recipe = recipe;
//      mealTitle.setText(this.recipe.getTitle());
//      mealDirections.setText(this.recipe.getDirections());
//    }
//  }

  private class MealAdapter extends BaseAdapter {

    @Override
    public int getCount() {
      return meals.size();
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

      title.setText(meals.get(position).getTitle());
      directions.setText(Html.fromHtml(meals.get(position).getDirections()));

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
      meals.clear();
      meals.addAll(recipes);
      mealAdapter.notifyDataSetChanged();
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

}
