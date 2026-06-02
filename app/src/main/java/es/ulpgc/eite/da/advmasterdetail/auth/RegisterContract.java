package es.ulpgc.eite.da.advmasterdetail.auth;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.data.GameRepositoryContract;
import es.ulpgc.eite.da.advmasterdetail.data.UserItem;

public interface RegisterContract {

    interface View {
        void injectPresenter(Presenter presenter);

        void displayRegisterData(RegisterViewModel viewModel);

        void showMessage(String message);

        void navigateToLoginScreen();

        void navigateToProductListScreen(boolean loggedIn);
    }

    interface Presenter {
        void injectView(WeakReference<View> view);

        void injectModel(Model model);

        void onCreateCalled();

        void onRecreateCalled();

        void onPauseCalled(String name, String email, String password);

        void onDestroyCalled();

        void registerButtonClicked(String name, String email, String password);

        void guestButtonClicked();

        void loginButtonClicked();
    }

    interface Model {
        void registerUser(
                UserItem user, GameRepositoryContract.RegisterUserCallback callback);

        void saveLoggedUser(UserItem user);

        void clearSession();
    }
}