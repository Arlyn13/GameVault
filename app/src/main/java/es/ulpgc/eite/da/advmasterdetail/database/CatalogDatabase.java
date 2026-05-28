package es.ulpgc.eite.da.advmasterdetail.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import es.ulpgc.eite.da.advmasterdetail.data.CategoryItem;
import es.ulpgc.eite.da.advmasterdetail.data.ProductItem;


@Database(entities = {CategoryItem.class, ProductItem.class}, version = 1)
public abstract class CatalogDatabase extends RoomDatabase {

  public abstract CategoryDao categoryDao();
  public abstract ProductDao productDao();
}
