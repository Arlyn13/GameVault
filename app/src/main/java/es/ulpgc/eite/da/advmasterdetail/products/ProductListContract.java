package es.ulpgc.eite.da.advmasterdetail.products;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.data.CategoryItem;
import es.ulpgc.eite.da.advmasterdetail.data.ProductItem;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;


interface ProductListContract {

  interface View {
    void injectPresenter(Presenter presenter);

    void displayProductListData(ProductListViewModel viewModel);

    void navigateToProductDetailScreen();
  }

  interface Presenter {
    void injectView(WeakReference<View> view);
    void injectModel(Model model);

    void fetchProductListData();
    void selectedProductData(ProductItem item);

    void onCreateCalled();

    void onRecreateCalled();

      void onPauseCalled();
  }

  interface Model {

    void fetchProductListData(
            CategoryItem category, RepositoryContract.GetProductListCallback callback);
  }

}