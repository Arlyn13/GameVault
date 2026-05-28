package es.ulpgc.eite.da.advmasterdetail.categories;

import android.util.Log;

import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;


public class CategoryListModel implements CategoryListContract.Model {

  public static String TAG = "AdvMasterDetail.CategoryListModel";

  private RepositoryContract repository;

  public CategoryListModel(RepositoryContract repository) {
    this.repository = repository;

  }

  @Override
  public void fetchCategoryListData(
      final RepositoryContract.GetCategoryListCallback callback) {

    Log.e(TAG, "fetchCategoryListData()");


    repository.loadCatalog(true, error -> {
      if (!error) {
        repository.getCategoryList(callback);
      }
    });

    /*
    repository.loadCatalog(
        true, new RepositoryContract.FetchCatalogDataCallback() {

      @Override
      public void onCatalogDataFetched(boolean error) {
        if(!error) {
          repository.getCategoryList(callback);
        }
      }
    });
    */

  }

}
