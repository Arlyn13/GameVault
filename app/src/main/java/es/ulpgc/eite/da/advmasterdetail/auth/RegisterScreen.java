package es.ulpgc.eite.da.advmasterdetail.auth;

import androidx.fragment.app.FragmentActivity;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.app.AuthMediator;
import es.ulpgc.eite.da.advmasterdetail.app.SessionManager;
import es.ulpgc.eite.da.advmasterdetail.data.GameRepository;
import es.ulpgc.eite.da.advmasterdetail.data.GameRepositoryContract;

public class RegisterScreen {

    public static void configure(RegisterContract.View view) {

        WeakReference<FragmentActivity> context =
                new WeakReference<>((FragmentActivity) view);

        AuthMediator mediator = AuthMediator.getInstance();
        RegisterContract.Presenter presenter = new RegisterPresenter(mediator);

        GameRepositoryContract repository =
                GameRepository.getInstance(context.get());

        SessionManager sessionManager =
                new SessionManager(context.get().getApplicationContext());

        RegisterContract.Model model =
                new RegisterModel(repository, sessionManager);

        presenter.injectView(new WeakReference<>(view));
        presenter.injectModel(model);
        view.injectPresenter(presenter);
    }
}