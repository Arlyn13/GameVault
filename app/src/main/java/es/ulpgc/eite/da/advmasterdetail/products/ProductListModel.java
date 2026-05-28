package es.ulpgc.eite.da.advmasterdetail.products;

import android.util.Log;

import es.ulpgc.eite.da.advmasterdetail.data.CategoryItem;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;


public class ProductListModel implements ProductListContract.Model {

  public static String TAG = "AdvMasterDetail.ProductListModel";


  private RepositoryContract repository;

  public ProductListModel(RepositoryContract repository){
    this.repository = repository;
  }

  @Override
  public void fetchProductListData(
          CategoryItem category, RepositoryContract.GetProductListCallback callback) {

    Log.e(TAG, "fetchProductListData()");
    repository.getProductList(category, callback);
  }

}
