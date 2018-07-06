package com.nicholaslocicero.focus.reciplee;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.nicholaslocicero.focus.reciplee.model.db.Reciplee;
import com.nicholaslocicero.focus.reciplee.model.entity.Ingredient;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroceryListFragment extends Fragment {

  private RecyclerView mIngredientRecyclerView;
  private List<Ingredient> mIngredientsList = new ArrayList<>();
  private ListAdapter mIngredientListAdapter;
  private Button mRecipeButton;
  private EditText mRecipeText;
  private Button mIngredientButton;
  private EditText mIngredientText;
  private Random rng = new Random();
  private boolean startup = true;



  public GroceryListFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_grocery_list, container, false);

    mIngredientRecyclerView = (RecyclerView) view.findViewById(R.id.ingredient_list_recycler_view);
    mIngredientRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    mIngredientButton = view.findViewById(R.id.add_ingredient_button);
    mIngredientText = view.findViewById(R.id.add_ingredient_text);
    mRecipeButton = view.findViewById(R.id.add_recipe_button);
    mRecipeText = view.findViewById(R.id.add_recipe_text);
    mIngredientListAdapter = new ListAdapter(mIngredientsList);
    mIngredientRecyclerView.setAdapter(mIngredientListAdapter);

    refreshList();

    mIngredientButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!mRecipeText.getText().toString().equals("")) {

          Ingredient ingredient = new Ingredient();
          ingredient.setName(mIngredientText.getText().toString());
          new IngredientInsert().execute(ingredient);
          mIngredientText.setText("");
        }
      }
    });

    // TODO SET UP AUTOTEXT VIEW FOR RECIPLEE_DB

//    mRecipeButton.setOnClickListener(new OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        try {
//
//        } catch (Exception e) {
//
//        }
//        if (!mIngredientText.getText().toString().equals("")) {
//          Ingredient ingredient = new Ingredient();
//          ingredient.setName(mIngredientText.getText().toString());
//          ingredient.setAmount(Integer.toString(rng.nextInt(10)));
//          ingredient.setMeasurement("cups");
//          new IngredientInsert().execute(ingredient);
//          mIngredientText.setText("");
//        }
//      }
//    });

    return view;
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
