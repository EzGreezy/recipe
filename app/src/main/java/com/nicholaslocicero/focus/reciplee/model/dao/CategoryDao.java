package com.nicholaslocicero.focus.reciplee.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import com.nicholaslocicero.focus.reciplee.model.entity.Category;
import java.util.List;

@Dao
public interface CategoryDao {

  @Insert
  long insert(Category category);

  @Insert
  List<Long> insert(Category... categories);

  @Insert
  List<Long> insert(List<Category> categories);

}
