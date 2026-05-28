package es.ulpgc.eite.da.advmasterdetail.categories;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;
import es.ulpgc.eite.da.advmasterdetail.data.CategoryItem;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;


public class CategoryListPresenter implements CategoryListContract.Presenter {

  public static String TAG = "AdvMasterDetail.CategoryListPresenter";

  private WeakReference<CategoryListContract.View> view;
  private CategoryListState state;
  private CategoryListContract.Model model;
  private CatalogMediator mediator;


  public CategoryListPresenter(CatalogMediator mediator) {
    this.mediator = mediator;
  }

  @Override
  public void onCreateCalled() {
    // Log.e(TAG, "onCreateCalled");

    state = new CategoryListState();
  }

  @Override
  public void onRecreateCalled() {
    // Log.e(TAG, "onRecreateCalled");

    state = mediator.getCategoryListState();
  }

  @Override
  public void onPauseCalled() {
    Log.e(TAG, "onPauseCalled()");

    mediator.setCategoryListState(state);
  }

  @Override
  public void fetchCategoryListData() {
    // Log.e(TAG, "fetchCategoryListData");

    // call the model
    model.fetchCategoryListData(categories -> {
      state.categories = categories;

      view.get().displayCategoryListData(state);
    });

    /*model.fetchCategoryListData(new RepositoryContract.GetCategoryListCallback() {

      @Override
      public void setCategoryList(List<CategoryItem> categories) {
        state.categories = categories;

        view.get().displayCategoryListData(state);
      }
    });*/

  }


  @Override
  public void selectedCategoryData(CategoryItem item) {
    mediator.setCategory(item);
    view.get().navigateToProductListScreen();
  }


  @Override
  public void injectView(WeakReference<CategoryListContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(CategoryListContract.Model model) {
    this.model = model;
  }


}
