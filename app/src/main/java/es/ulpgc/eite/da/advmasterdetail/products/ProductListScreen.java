package es.ulpgc.eite.da.advmasterdetail.products;

import androidx.fragment.app.FragmentActivity;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;
import es.ulpgc.eite.da.advmasterdetail.data.CatalogRepository;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;


public class ProductListScreen {

  public static void configure(ProductListContract.View view) {

    WeakReference<FragmentActivity> context =
        new WeakReference<>((FragmentActivity) view);

    CatalogMediator mediator = CatalogMediator.getInstance();
    ProductListContract.Presenter presenter = new ProductListPresenter(mediator);

    RepositoryContract repository = CatalogRepository.getInstance(context.get());
    ProductListModel model = new ProductListModel(repository);

    presenter.injectView(new WeakReference<>(view));
    presenter.injectModel(model);
    view.injectPresenter(presenter);

  }


}
