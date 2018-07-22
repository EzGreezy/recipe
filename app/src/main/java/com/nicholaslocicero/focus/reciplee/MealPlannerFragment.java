package com.nicholaslocicero.focus.reciplee;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.nicholaslocicero.focus.reciplee.model.entity.Ingredient;
import java.util.List;


/**
 * A simple {@link Fragment} subclass. Use the {@link MealPlannerFragment#newInstance} factory
 * method to create an instance of this fragment.
 */
public class MealPlannerFragment extends Fragment {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;


  public MealPlannerFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment MealPlannerFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static MealPlannerFragment newInstance(String param1, String param2) {
    MealPlannerFragment fragment = new MealPlannerFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_meal_planner, container, false);
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

}
