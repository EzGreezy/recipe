package com.nicholaslocicero.focus.reciplee;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

public class RecipePickerFragment extends DialogFragment {

  public static final String EXTRA_RECIPE =
      "com.nicholaslocicero.focus.reciplee.recipe";

  private static final String ARG_RECIPE_TITLE = "recipe_title";

  public static RecipePickerFragment newInstance(String recipe_title) {
    Bundle args = new Bundle();
    args.putSerializable(ARG_RECIPE_TITLE, recipe_title);

    RecipePickerFragment fragment = new RecipePickerFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    String recipe_title = (String) getArguments().getSerializable(ARG_RECIPE_TITLE);

    View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_recipe, null);

    return new AlertDialog.Builder(getActivity())
        .setView(v)
        .setTitle("Temp test")
        .setMessage("Temp\nWith a new\nLine?\n* Ingredient 1\n* Ingredient 2\n* Ingredient 3\n....")
        .setPositiveButton(android.R.string.ok,
            new OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {

              }
            })
        .create();
  }

  private void sendResult(int resultCode, String recipe_title) {
    if (getTargetFragment() == null) {
      return;
    }

    Intent intent = new Intent();
    intent.putExtra(EXTRA_RECIPE, recipe_title);

    getTargetFragment()
        .onActivityResult(getTargetRequestCode(), resultCode, intent);
  }
}
