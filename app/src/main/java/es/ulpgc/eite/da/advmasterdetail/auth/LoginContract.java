package es.ulpgc.eite.da.advmasterdetail.auth;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.data.GameRepositoryContract;

public interface LoginContract {

    interface View {
        void injectPresenter(Presenter presenter);

        void displayLoginData(LoginViewModel viewModel);

        void showMessage(String message);

        void navigateToRegisterScreen();

        void navigateToProductListScreen(boolean loggedIn);
    }

    interface Presenter {
        void injectView(WeakReference<View> view);

        void injectModel(Model model);

        void onCreateCalled();

        void onRecreateCalled();

        void onPauseCalled(String email, String password);

        void onDestroyCalled();

        void loginButtonClicked(String email, String password);

        void guestButtonClicked();

        void registerButtonClicked();
    }

    interface Model {
        void loginUser(
                String email,
                String password,
                GameRepositoryContract.LoginUserCallback callback);

        void saveLoggedUser(es.ulpgc.eite.da.advmasterdetail.data.UserItem user);

        void clearSession();
    }
}