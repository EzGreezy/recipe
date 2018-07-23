package com.nicholaslocicero.focus.reciplee;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.List;
import java.util.Map;

public class ShoppingListAdapter extends BaseExpandableListAdapter {

  private Context context;
  private Map<String, List<String>> shoppingListMap;
  private List<String> shoppingIngredientsList;

  public ShoppingListAdapter(Context context, Map<String, List<String>> shoppingListMap, List<String> shoppingIngredientsList) {
    this.context = context;
    this.shoppingListMap = shoppingListMap;
    this.shoppingIngredientsList = shoppingIngredientsList;

  }

  @Override
  public int getGroupCount() {
    return shoppingIngredientsList.size();
  }

  @Override
  public int getChildrenCount(int groupPosition) {
    return shoppingListMap.get(shoppingIngredientsList.get(groupPosition)).size();
  }

  @Override
  public Object getGroup(int groupPosition) {
    return shoppingIngredientsList.get(groupPosition);
  }

  @Override
  public Object getChild(int parent, int child) {
    return shoppingListMap.get(shoppingIngredientsList.get(parent)).get(child);
  }

  @Override
  public long getGroupId(int groupPosition) {
    return groupPosition;
  }

  @Override
  public long getChildId(int parent, int child) {
    return child;
  }

  @Override
  public boolean hasStableIds() {
    return false;
  }

  @Override
  public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
      ViewGroup parent) {
    String groupTitle = (String) getGroup(groupPosition);
    if (convertView == null) {
      LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = inflater.inflate(R.layout.parent_layout, parent, false);
    }
    TextView parentTextView = (TextView) convertView.findViewById(R.id.parent_text);
    parentTextView.setTypeface(null, Typeface.BOLD);
    parentTextView.setText(groupTitle);
    return convertView;
  }

  @Override
  public View getChildView(int parent, int child, boolean lastChild,
      View convertView, final ViewGroup parentView) {
    String childTitle = (String) getChild(parent, child);
    if (convertView == null) {
      LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = inflater.inflate(R.layout.child_layout, parentView, false);
    }
    TextView childTextView = (TextView) convertView.findViewById(R.id.child_txt);
    childTextView.setText(Html.fromHtml(childTitle));
    return convertView;
  }

  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return true;
  }
}
