package es.ulpgc.eite.da.advmasterdetail.games;

import androidx.fragment.app.FragmentActivity;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.app.GameMediator;
import es.ulpgc.eite.da.advmasterdetail.app.SessionManager;
import es.ulpgc.eite.da.advmasterdetail.data.GameRepository;
import es.ulpgc.eite.da.advmasterdetail.data.GameRepositoryContract;

public class GameListScreen {

    public static void configure(GameListContract.View view) {

        WeakReference<FragmentActivity> context =
                new WeakReference<>((FragmentActivity) view);

        GameMediator mediator = GameMediator.getInstance();
        GameListContract.Presenter presenter =
                new GameListPresenter(mediator);

        GameRepositoryContract repository =
                GameRepository.getInstance(context.get());

        SessionManager sessionManager =
                new SessionManager(context.get().getApplicationContext());

        GameListContract.Model model =
                new GameListModel(repository, sessionManager);

        presenter.injectView(new WeakReference<>(view));
        presenter.injectModel(model);
        view.injectPresenter(presenter);
    }
}