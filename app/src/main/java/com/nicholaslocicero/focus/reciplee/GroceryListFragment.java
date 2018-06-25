package com.nicholaslocicero.focus.reciplee;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroceryListFragment extends Fragment {

  private RecyclerView mIngredientRecyclerView;
  private ListAdapter mIgredientListAdapter;


  public GroceryListFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_grocery_list, container, false);

    mIngredientRecyclerView = (RecyclerView) view.findViewById(R.id.ingredient_list_recycler_view);
    mIngredientRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    updateUI();

    return view;
  }

  private void updateUI() {
    KitchenIngredients kitchenIngredients = KitchenIngredients.get(getActivity());
    List<Ingredient> ingredients = kitchenIngredients.getIngredients();
    mIgredientListAdapter = new ListAdapter(ingredients);
    mIngredientRecyclerView.setAdapter(mIgredientListAdapter);

  }

  private class ListHolder extends RecyclerView.ViewHolder {
    private Ingredient mIngredient;
    private TextView mIngredientTextView;
    private TextView mAmountTextView;

    public ListHolder(LayoutInflater inflater, ViewGroup parent) {
      super(inflater.inflate(R.layout.list_item_ingredient, parent, false));

      mIngredientTextView = (TextView) itemView.findViewById(R.id.ingredient);
      mAmountTextView = (TextView) itemView.findViewById(R.id.quantity);
    }

    public void bind(Ingredient ingredient) {
      mIngredient = ingredient;
      mIngredientTextView.setText(mIngredient.getIngredient());
      mAmountTextView.setText(mIngredient.getAmount());
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
}
