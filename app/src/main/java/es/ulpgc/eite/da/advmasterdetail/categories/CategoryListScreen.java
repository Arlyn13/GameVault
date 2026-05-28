package es.ulpgc.eite.da.advmasterdetail.categories;

import androidx.fragment.app.FragmentActivity;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;
import es.ulpgc.eite.da.advmasterdetail.data.CatalogRepository;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;


public class CategoryListScreen {

  public static void configure(CategoryListContract.View view) {

    WeakReference<FragmentActivity> context =
        new WeakReference<>((FragmentActivity) view);

    CatalogMediator mediator = CatalogMediator.getInstance();
    CategoryListContract.Presenter presenter=new CategoryListPresenter(mediator);

    RepositoryContract repository = CatalogRepository.getInstance(context.get());
    CategoryListModel model = new CategoryListModel(repository);

    presenter.injectView(new WeakReference<>(view));
    presenter.injectModel(model);
    view.injectPresenter(presenter);

  }


}
