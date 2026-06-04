package es.ulpgc.eite.da.advmasterdetail.games;

import androidx.fragment.app.FragmentActivity;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.app.GameMediator;
import es.ulpgc.eite.da.advmasterdetail.app.SessionManager;
import es.ulpgc.eite.da.advmasterdetail.data.GameRepository;
import es.ulpgc.eite.da.advmasterdetail.data.GameRepositoryContract;

public class GameDetailScreen {

    public static void configure(GameDetailContract.View view) {

        WeakReference<FragmentActivity> context =
                new WeakReference<>((FragmentActivity) view);

        GameMediator mediator = GameMediator.getInstance();

        GameDetailContract.Presenter presenter =
                new GameDetailPresenter(mediator);

        GameRepositoryContract repository =
                GameRepository.getInstance(context.get());

        SessionManager sessionManager =
                new SessionManager(context.get().getApplicationContext());

        GameDetailContract.Model model =
                new GameDetailModel(repository, sessionManager);

        presenter.injectView(new WeakReference<>(view));
        presenter.injectModel(model);
        view.injectPresenter(presenter);
    }
}