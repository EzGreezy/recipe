package com.nicholaslocicero.focus.reciplee;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.nicholaslocicero.focus.reciplee.model.db.Reciplee;
import com.nicholaslocicero.focus.reciplee.model.entity.Recipe;
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


  private class MealHolder extends RecyclerView.ViewHolder {
    private Recipe recipe;
    private TextView mealTitle;
    private TextView mealDirections;

    public MealHolder(LayoutInflater inflater, ViewGroup parent) {
      super(inflater.inflate(R.layout.meal_planner_item, parent, false));

      mealTitle = (TextView) itemView.findViewById(R.id.meal_title);
      mealDirections = (TextView) itemView.findViewById(R.id.meal_directions);
    }

    public void bind(Recipe recipe) {
      this.recipe = recipe;
      mealTitle.setText(this.recipe.getTitle());
      mealDirections.setText(this.recipe.getDirections());
    }
  }

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

}
