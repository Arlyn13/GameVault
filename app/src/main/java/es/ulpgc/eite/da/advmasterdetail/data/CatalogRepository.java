package es.ulpgc.eite.da.advmasterdetail.data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.room.Room;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import es.ulpgc.eite.da.advmasterdetail.database.CatalogDatabase;
import es.ulpgc.eite.da.advmasterdetail.database.CategoryDao;
import es.ulpgc.eite.da.advmasterdetail.database.ProductDao;


public class CatalogRepository implements RepositoryContract {

  public static String TAG = CatalogRepository.class.getSimpleName();


  public static final String DB_FILE = "catalog.db";
  public static final String JSON_FILE = "catalog.json";
  public static final String JSON_ROOT = "categories";

  private static CatalogRepository INSTANCE;

  private CatalogDatabase database;
  private Context context;


  public static RepositoryContract getInstance(Context context) {
    if(INSTANCE == null){
      INSTANCE = new CatalogRepository(context);
    }

    return INSTANCE;
  }

  private CatalogRepository(Context context) {
    this.context = context;

    database = Room.databaseBuilder(
        context, CatalogDatabase.class, DB_FILE
    ).build();

  }

  @Override
  public void loadCatalog(
      final boolean clearFirst, final FetchCatalogDataCallback callback) {

    AsyncTask.execute(() -> {
      if(clearFirst) {
        database.clearAllTables();
      }

      boolean error = false;
      if(getCategoryDao().loadCategories().size() == 0 ) {
        error = !loadCatalogFromJSON(loadJSONFromAsset());
      }

      if(callback != null) {
        callback.onCatalogDataFetched(error);
      }
    });

  }

  @Override
  public void getProductList(
      final CategoryItem category, final GetProductListCallback callback) {

    getProductList(category.id, callback);
  }


  @Override
  public void getProductList(
      final int categoryId, final GetProductListCallback callback) {

    AsyncTask.execute(() -> {
      if(callback != null) {
        callback.setProductList(getProductDao().loadProducts(categoryId));
      }
    });

  }


  @Override
  public void getProduct(final int id, final GetProductCallback callback) {

    AsyncTask.execute(() -> {
      if(callback != null) {
        callback.setProduct(getProductDao().loadProduct(id));
      }
    });
  }

  @Override
  public void getCategory(final int id, final GetCategoryCallback callback) {

    AsyncTask.execute(() -> {
      if(callback != null) {
        callback.setCategory(getCategoryDao().loadCategory(id));
      }
    });

  }

  @Override
  public void getCategoryList(final GetCategoryListCallback callback) {
    AsyncTask.execute(() -> {
      if(callback != null) {
        callback.setCategoryList(getCategoryDao().loadCategories());
      }
    });

  }

  @Override
  public void deleteProduct(
      final ProductItem product, final DeleteProductCallback callback) {

    AsyncTask.execute(() -> {
      if(callback != null) {
        getProductDao().deleteProduct(product);
        callback.onProductDeleted();
      }
    });
  }

  @Override
  public void updateProduct(
      final ProductItem product, final UpdateProductCallback callback) {

    AsyncTask.execute(() -> {
      if(callback != null) {
        getProductDao().updateProduct(product);
        callback.onProductUpdated();
      }
    });
  }


  @Override
  public void deleteCategory(
      final CategoryItem category, final DeleteCategoryCallback callback) {

    AsyncTask.execute(() -> {
      if(callback != null) {
        getCategoryDao().deleteCategory(category);
        callback.onCategoryDeleted();
      }
    });
  }

  @Override
  public void updateCategory(
      final CategoryItem category, final UpdateCategoryCallback callback) {

    AsyncTask.execute(() -> {
      if(callback != null) {
        getCategoryDao().updateCategory(category);
        callback.onCategoryUpdated();
      }
    });
  }


  private CategoryDao getCategoryDao() {
    return database.categoryDao();
  }

  private ProductDao getProductDao() {
    return database.productDao();
  }


  private boolean loadCatalogFromJSON(String json) {
    Log.e(TAG, "loadCatalogFromJSON()");

    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();

    try {

      JSONObject jsonObject = new JSONObject(json);
      JSONArray jsonArray = jsonObject.getJSONArray(JSON_ROOT);

      if (jsonArray.length() > 0) {

        final List<CategoryItem> categories = Arrays.asList(
            gson.fromJson(jsonArray.toString(), CategoryItem[].class)
        );

        for (CategoryItem category: categories) {
          getCategoryDao().insertCategory(category);
        }

        for (CategoryItem category: categories) {
          for (ProductItem product: category.items) {
            product.categoryId = category.id;
            getProductDao().insertProduct(product);
          }
        }

        return true;
      }

    } catch (JSONException error) {
      Log.e(TAG, "error: " + error);
    }

    return false;
  }



  private String loadJSONFromAsset( )  {

    //Log.e(TAG, "loadJSONFromAsset()");

    String json = null;

    try {

      InputStream inputStream = context.getAssets().open(JSON_FILE);
      BufferedReader reader =
              new BufferedReader(new InputStreamReader(inputStream));
      StringBuilder stringBuilder = new StringBuilder();
      String line;

      while ((line = reader.readLine()) != null) {
        stringBuilder.append(line);
      }

      reader.close();
      json = stringBuilder.toString();

      //Log.e(TAG, "JSON: " + json);

    } catch (IOException error) {
      Log.e(TAG, "error: " + error);
    }

    return json;
  }


}
