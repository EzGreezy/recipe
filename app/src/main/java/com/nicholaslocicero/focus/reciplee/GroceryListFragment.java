package com.nicholaslocicero.focus.reciplee;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import com.nicholaslocicero.focus.reciplee.model.db.Reciplee;
import com.nicholaslocicero.focus.reciplee.model.entity.Ingredient;
import com.nicholaslocicero.focus.reciplee.model.entity.Recipe;
import com.nicholaslocicero.focus.reciplee.model.entity.ShoppingItem;
import com.nicholaslocicero.focus.reciplee.model.pojo.ShoppingListAssembled;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroceryListFragment extends Fragment {

  private static final String DIALOG_RECIPE = "DialogRecipe";

  private static final int REQUEST_RECIPE = 0;

  private AutoCompleteAdapter recipeTitlesAdapter;
  private List<String> recipeTitles = new ArrayList<>();
  private Button addIngredientButton;
  private EditText addIngredientText;
  private String recipeTitle = "";
  private String recipeDirections = "";
  private Map<String,List<String>> shoppingListMap = new HashMap<>();
  private List<String> shoppingListIngredients = new ArrayList<>();
  private ExpandableListView shoppingListExpandable;
  private ShoppingListAdapter adapter;
  private String addIngredientDirectText = "";
  private String addIngredientDirectAmount = "";

  public GroceryListFragment() {

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // FIXME On the first installation, when it starts up on emulator, the autocomplete text doesn't work. You have to completely close the app and restart it.
    View view = inflater.inflate(R.layout.fragment_grocery_list, container, false);
    new RecipesQuery().execute();
    shoppingListExpandable = (ExpandableListView) view.findViewById(R.id.shopping_expandable_list_view);
    adapter = new ShoppingListAdapter(getContext(), shoppingListMap, shoppingListIngredients);
    shoppingListExpandable.setAdapter(adapter);
    refreshShoppingList();
    recipeTitlesAdapter = new AutoCompleteAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, R.id.add_recipe_text_array);
    final AutoCompleteTextView addRecipeSuggestions = (AutoCompleteTextView) view.findViewById(R.id.add_recipe_text);
    addRecipeSuggestions.setAdapter(recipeTitlesAdapter);

    addRecipeSuggestions.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        recipeTitle = ((TextView) view).getText().toString();
        new RecipeDirections().execute(recipeTitle);
        addRecipeSuggestions.requestFocus();
      }
    });

    addIngredientText = view.findViewById(R.id.add_ingredient_text);
    addIngredientButton = view.findViewById(R.id.add_ingredient_button);

    addIngredientButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        AlertDialog.Builder dialog = new Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.add_ingredient_dialog, null);
        final EditText ingredientEditText = (EditText) dialogView.findViewById(R.id.dialog_add_ingredient_item);
        addIngredientDirectText = (String) addIngredientText.getText().toString();
        ingredientEditText.setHint("How much " + addIngredientDirectText + "?");
        Button add = (Button) dialogView.findViewById(R.id.dialog_add_ingredient_add_button);
        Button back = (Button) dialogView.findViewById(R.id.dialog_add_ingredient_cancel_button);
        dialog.setView(dialogView);
        final AlertDialog dialogBuilt = dialog.create();
        dialogBuilt.show();
        back.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
            dialogBuilt.dismiss();
          }
        });
        add.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
            new AddIngredientToShoppingList().execute(addIngredientDirectText);
            addIngredientText.getText().clear();
            addIngredientDirectAmount = ingredientEditText.getText().toString();
            ingredientEditText.getText().clear();
            dialogBuilt.dismiss();
          }
        });
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
      this.items.addAll(collection);
    }

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

  private void refreshShoppingList() {
    new ShoppingItemQuery().execute();
  }

  private class ShoppingItemQuery extends AsyncTask<Void, Void, List<ShoppingListAssembled>> {

    @Override
    protected List<ShoppingListAssembled> doInBackground(Void... voids) {
      return Reciplee.getInstance(getContext()).getShoppingItemDao().assembleShoppingList();
    }
    @Override
    protected void onPostExecute(List<ShoppingListAssembled> shoppingItems) {
      shoppingListMap.clear();
      shoppingListIngredients.clear();
      for (ShoppingListAssembled item : shoppingItems) {
        if (shoppingListMap.containsKey(item.getIngredient())) {
          if (item.getDescription() != null) {
            shoppingListMap.get(item.getIngredient()).add(item.getDescription());
          } else {
            shoppingListMap.get(item.getIngredient()).add(item.getItem());
          }
        } else {
          if (item.getDescription() != null) {
            shoppingListMap.put(item.getIngredient(), new ArrayList<String>());
            shoppingListMap.get(item.getIngredient()).add(item.getDescription());
          } else {
            shoppingListMap.put(item.getIngredient(), new ArrayList<String>());
            shoppingListMap.get(item.getIngredient()).add(item.getItem());
          }
        }
      }
      for (String key : shoppingListMap.keySet()) {
        shoppingListIngredients.add(key);
      }
      Collections.sort(shoppingListIngredients);
      adapter.notifyDataSetChanged();
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

  private class RecipeDirections extends AsyncTask<String, Void, List<String>> {

    @Override
    protected List<String> doInBackground(String... strings) {
      return Reciplee.getInstance(getContext()).getRecipeDao().selectDirectionsFromTitle(strings[0]);
    }

    @Override
    protected void onPostExecute(List<String> strings) {
      // TODO add ingredients to recipe dialog
      recipeDirections = strings.get(0);
      recipeDirections = "<h4>Directions</h4>" + recipeDirections.replace("& ", "\n- ");
      AlertDialog.Builder dialog = new Builder(getContext());
      View dialogView = getLayoutInflater().inflate(R.layout.custom_dialog, null);
      TextView title = (TextView) dialogView.findViewById(R.id.recipe_title);
      TextView directions = (TextView) dialogView.findViewById(R.id.recipe_directions);
      Button add = (Button) dialogView.findViewById(R.id.add);
      Button back = (Button) dialogView.findViewById(R.id.back);
      title.setText(recipeTitle);
      directions.setText(Html.fromHtml(recipeDirections), BufferType.SPANNABLE);
      dialog.setView(dialogView);
      final AlertDialog dialogBuilt = dialog.create();
      dialogBuilt.show();
      back.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          dialogBuilt.dismiss();
        }
      });
      add.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          new GetRecipe().execute(recipeTitle);
          dialogBuilt.dismiss();
        }
      });
    }
  }

  private class GetRecipe extends AsyncTask<String, Void, List<Recipe>> {

    @Override
    protected List<Recipe> doInBackground(String... strings) {
      return Reciplee.getInstance(getContext()).getRecipeDao().selectRecipeByTitle(strings[0]);
    }

    @Override
    protected void onPostExecute(List<Recipe> recipes) {
      ShoppingItem shoppingItem = new ShoppingItem();
      shoppingItem.setRecipe_id(recipes.get(0).getId());
      new InsertShoppingItem().execute(shoppingItem);
    }
  }

  private class InsertShoppingItem extends AsyncTask<ShoppingItem, Void, Long> {

    @Override
    protected Long doInBackground(ShoppingItem... shoppingItems) {
      return Reciplee.getInstance(getContext()).getShoppingItemDao().insert(shoppingItems[0]);
    }

    @Override
    protected void onPostExecute(Long recipes) {
      refreshShoppingList();
    }
  }

  private class IngredientInsert extends AsyncTask<Ingredient, Void, Long> {

    @Override
    protected Long doInBackground(Ingredient... ingredients) {
      return Reciplee.getInstance(getContext()).getIngredientDao().insert(ingredients[0]);
    }

    @Override
    protected void onPostExecute(Long aLong) {
      refreshShoppingList();
    }
  }

  private class AddIngredientToShoppingList extends AsyncTask<String, Void, Long> {

    @Override
    protected Long doInBackground(String... strings) {
      return Reciplee.getInstance(getContext()).getIngredientDao().getIngredientIdByName(strings[0]);
    }

    @Override
    protected void onPostExecute(Long id) {
      if (id == null) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(addIngredientDirectText);
        new InsertNewIngredient().execute(ingredient);
      } else {
        ShoppingItem shoppingItem = new ShoppingItem();
        shoppingItem.setIngredient_id(id);
        shoppingItem.setIngredient_item(addIngredientDirectAmount);
        new InsertShoppingItem().execute(shoppingItem);
      }
    }
  }

  private class InsertNewIngredient extends AsyncTask<Ingredient, Void, Long> {

    @Override
    protected Long doInBackground(Ingredient... ingredients) {
      return Reciplee.getInstance(getContext()).getIngredientDao().insert(ingredients[0]);
    }

    @Override
    protected void onPostExecute(Long id) {
      ShoppingItem shoppingItem = new ShoppingItem();
      shoppingItem.setIngredient_id(id);
      shoppingItem.setIngredient_item(addIngredientDirectAmount);
      new InsertShoppingItem().execute(shoppingItem);
    }
  }
}
