package com.nicholaslocicero.focus.reciplee.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import com.nicholaslocicero.focus.reciplee.model.entity.CategoryMap;
import java.util.List;

@Dao
public interface CategoryMapDao {

  @Insert
  long insert(CategoryMap categoryMap);

  @Insert
  List<Long> insert(CategoryMap... categoryMaps);

  @Insert
  List<Long> insert(List<CategoryMap> categoryMapsList);
}
