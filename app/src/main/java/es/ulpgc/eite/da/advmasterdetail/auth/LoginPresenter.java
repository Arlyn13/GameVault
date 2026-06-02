package es.ulpgc.eite.da.advmasterdetail.auth;

import android.util.Patterns;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.app.AuthMediator;

public class LoginPresenter implements LoginContract.Presenter {

    private WeakReference<LoginContract.View> view;
    private LoginContract.Model model;
    private LoginState state;
    private AuthMediator mediator;

    public LoginPresenter(AuthMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void onCreateCalled() {
        state = new LoginState();
    }

    @Override
    public void onRecreateCalled() {
        state = mediator.getLoginState();

        if (state == null) {
            state = new LoginState();
        }

        view.get().displayLoginData(state);
    }

    @Override
    public void onPauseCalled(String email, String password) {
        state.email = email;
        state.password = password;

        mediator.setLoginState(state);
    }

    @Override
    public void onDestroyCalled() {

    }

    @Override
    public void loginButtonClicked(String email, String password) {

        state.email = email.trim();
        state.password = password.trim();

        if (state.email.isEmpty()
                || !Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {

            view.get().showMessage("Introduce un email válido");
            return;
        }

        if (state.password.isEmpty()) {
            view.get().showMessage("Introduce la contraseña");
            return;
        }

        model.loginUser(state.email, state.password, user -> {

            if (user == null) {
                view.get().showMessage("Email o contraseña incorrectos");
                return;
            }

            model.saveLoggedUser(user);
            mediator.setUser(user);

            view.get().showMessage("Sesión iniciada");
            view.get().navigateToProductListScreen(true);
        });
    }

    @Override
    public void guestButtonClicked() {
        model.clearSession();
        view.get().navigateToProductListScreen(false);
    }

    @Override
    public void registerButtonClicked() {
        view.get().navigateToRegisterScreen();
    }

    @Override
    public void injectView(WeakReference<LoginContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(LoginContract.Model model) {
        this.model = model;
    }
}