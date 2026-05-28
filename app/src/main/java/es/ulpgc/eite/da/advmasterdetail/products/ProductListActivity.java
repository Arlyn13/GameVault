package es.ulpgc.eite.da.advmasterdetail.products;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.ulpgc.eite.da.advmasterdetail.R;
import es.ulpgc.eite.da.advmasterdetail.data.CategoryItem;
import es.ulpgc.eite.da.advmasterdetail.data.ProductItem;
import es.ulpgc.eite.da.advmasterdetail.product.ProductDetailActivity;


public class ProductListActivity
    extends AppCompatActivity implements ProductListContract.View {

  public static String TAG = "AdvMasterDetail.ProductListActivity";

  ProductListContract.Presenter presenter;

  private ProductListAdapter listAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_product_list);
    setTitle(R.string.title_product_list);

    // do the setup
    ProductListScreen.configure(this);

    initProductListContainer();

    // do some work
    if(savedInstanceState == null) {
      presenter.onCreateCalled();

    }else{
      presenter.onRecreateCalled();
    }
  }


  @Override
  protected void onResume() {
    super.onResume();

    // do some work
    presenter.fetchProductListData();
  }

  @Override
  protected void onPause() {
    super.onPause();

    presenter.onPauseCalled();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  private void initProductListContainer() {

    listAdapter = new ProductListAdapter(view -> {
      ProductItem item = (ProductItem) view.getTag();
      presenter.selectedProductData(item);
    });

    RecyclerView recyclerView = findViewById(R.id.product_recycler);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(listAdapter);
  }

  @Override
  public void navigateToProductDetailScreen() {
    Intent intent = new Intent(this, ProductDetailActivity.class);
    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }

  @Override
  public void displayProductListData(final ProductListViewModel viewModel) {
    Log.e(TAG, "displayProductListData");

    runOnUiThread(() -> {

      // deal with the data
      listAdapter.setItems(viewModel.products);
    });

  }

  @Override
  public void injectPresenter(ProductListContract.Presenter presenter) {
    this.presenter = presenter;
  }

}
