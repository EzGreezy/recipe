package com.nicholaslocicero.focus.reciplee;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.nicholaslocicero.focus.reciplee.model.db.Reciplee;
import com.nicholaslocicero.focus.reciplee.model.entity.Ingredient;
import com.nicholaslocicero.focus.reciplee.model.entity.Recipe;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroceryListFragment extends Fragment {

  private static final String DIALOG_RECIPE = "DialogRecipe";

  private static final int REQUEST_ID = 0;

  private RecyclerView mIngredientRecyclerView;
  private List<Ingredient> mIngredientsList = new ArrayList<>();
  private ListAdapter mIngredientListAdapter;
  private AutoCompleteAdapter recipeTitlesAdapter;
  private List<String> recipeTitles = new ArrayList<>();
  private Button mRecipeButton;
  private EditText mRecipeText;
  private Button mIngredientButton;
  private EditText mIngredientText;
  private Random rng = new Random();
  private boolean startup = true;




  public GroceryListFragment() {

  }




  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_grocery_list, container, false);
    new RecipesQuery().execute();



    mIngredientRecyclerView = (RecyclerView) view.findViewById(R.id.ingredient_list_recycler_view);
    mIngredientRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    mIngredientButton = view.findViewById(R.id.add_ingredient_button);
    mRecipeButton = view.findViewById(R.id.add_recipe_button);


    recipeTitlesAdapter = new AutoCompleteAdapter(getContext(),
        android.R.layout.simple_dropdown_item_1line, R.id.add_recipe_text_array);
    final AutoCompleteTextView addRecipeSuggestions = (AutoCompleteTextView) view.findViewById(R.id.add_recipe_text);
    addRecipeSuggestions.setAdapter(recipeTitlesAdapter);
    refreshList();

    addRecipeSuggestions.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentManager manager = getFragmentManager();
        RecipePickerFragment dialog = RecipePickerFragment.newInstance(100l);
        dialog.setTargetFragment(GroceryListFragment.this, REQUEST_ID);
        dialog.show(manager, DIALOG_RECIPE);
      }
    });

    return view;
  }

  public class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable{

    Context context;
    int resource, textViewResourceId;
    List<String> items;
    List<String> tempItems;
    List<String> suggestions;

    public AutoCompleteAdapter(Context context, int resource, int textViewResourceId) {
      super(context, resource, textViewResourceId);
      this.context = context;
      this.resource = resource;
      this.textViewResourceId = textViewResourceId;
      this.items = new ArrayList<String>();
      this.tempItems = new ArrayList<String>(); // this makes the difference.
      this.suggestions = new ArrayList<String>();
    }

    @Override
    public void addAll(@NonNull Collection<? extends String> collection) {
      //super.addAll(collection);
      this.items.addAll(collection);
    }

//    @Override
//    public void clear() {
//      this.items.clear();
//      this.suggestions.clear();
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      View view = convertView;
      if (convertView == null) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
      }
      if (suggestions.size() > 0 && position < suggestions.size()) {
        String recipe = suggestions.get(position);
        if (recipe != null) {
          ((TextView) view).setText(recipe);
//        TextView lblName = (TextView) view.findViewById(R.id.add_recipe_text);
//        if (lblName != null)
//          lblName.setText(recipe);
        }
      }
      return view;
    }

    @Override
    public Filter getFilter() {
      return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {

      @Override
      protected FilterResults performFiltering(CharSequence constraint) {
        if (constraint != null) {
          suggestions.clear();
          for (String recipe : items) {
            if (recipe.toLowerCase().contains(constraint.toString().toLowerCase())) {
              suggestions.add(recipe);
            }
          }
          FilterResults filterResults = new FilterResults();
          filterResults.values = suggestions;
          filterResults.count = suggestions.size();
          return filterResults;
        } else {
          return new FilterResults();
        }
      }

      @Override
      protected void publishResults(CharSequence constraint, FilterResults results) {
        List<String> filterList = (ArrayList<String>) results.values;
        if (results != null && results.count > 0) {
          clear();
          AutoCompleteAdapter.super.addAll(filterList);
        }
      }
    };
  }

  private class ListHolder extends RecyclerView.ViewHolder {
    private Ingredient mIngredient;
    private TextView mIngredientTextView;
    private TextView mAmountTextView;

    public ListHolder(LayoutInflater inflater, ViewGroup parent) {
      super(inflater.inflate(R.layout.list_item_ingredient, parent, false));

      mIngredientTextView = (TextView) itemView.findViewById(R.id.name);
      mAmountTextView = (TextView) itemView.findViewById(R.id.quantity);
    }

    public void bind(Ingredient ingredient) {
      mIngredient = ingredient;
      mIngredientTextView.setText(mIngredient.getName());
    }
  }

  private class ListAdapter extends RecyclerView.Adapter<ListHolder> {
    private List<Ingredient> mIngredients;

    public ListAdapter(List<Ingredient> ingredients) {
      mIngredients = ingredients;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
      LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

      return new ListHolder(layoutInflater, viewGroup);
    }

    @Override
    public void onBindViewHolder(ListHolder listHolder, int i) {
      Ingredient ingredient = mIngredients.get(i);
      listHolder.bind(ingredient);
    }

    @Override
    public int getItemCount() {
      return mIngredients.size();
    }
  }

  private void refreshList() {
    new IngredientsQuery().execute();
  }

  private class IngredientsQuery extends AsyncTask<Void, Void, List<Ingredient>> {

    @Override
    protected List<Ingredient> doInBackground(Void... voids) {
      return Reciplee.getInstance(getContext()).getIngredientDao().select();
    }
    @Override
    protected void onPostExecute(List<Ingredient> ingredients) {
      mIngredientsList.clear();
      mIngredientsList.addAll(ingredients);
      if (startup) {
        startup = false;
      } else {
        mIngredientListAdapter.notifyItemInserted(0);
        mIngredientRecyclerView.scrollToPosition(0);
      }
    }
  }

  private class RecipesQuery extends AsyncTask<Void, Void, List<String>> {

    @Override
    protected List<String> doInBackground(Void... voids) {
      return Reciplee.getInstance(getContext()).getRecipeDao().selectRecipeTitles();
    }
    @Override
    protected void onPostExecute(List<String> recipes) {
      recipeTitlesAdapter.clear();
      recipeTitles.clear();
      recipeTitles.addAll(recipes);
      recipeTitlesAdapter.addAll(recipeTitles);
      recipeTitlesAdapter.notifyDataSetChanged();
    }
  }

  private class IngredientInsert extends AsyncTask<Ingredient, Void, Long> {

    @Override
    protected Long doInBackground(Ingredient... ingredients) {
      return Reciplee.getInstance(getContext()).getIngredientDao().insert(ingredients[0]);
    }

    @Override
    protected void onPostExecute(Long aLong) {
      refreshList();
    }
  }
}
