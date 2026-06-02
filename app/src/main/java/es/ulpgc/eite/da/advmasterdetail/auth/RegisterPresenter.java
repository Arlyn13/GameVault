package es.ulpgc.eite.da.advmasterdetail.auth;

import android.util.Patterns;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.app.AuthMediator;
import es.ulpgc.eite.da.advmasterdetail.data.UserItem;

public class RegisterPresenter implements RegisterContract.Presenter {

    private WeakReference<RegisterContract.View> view;
    private RegisterContract.Model model;
    private RegisterState state;
    private AuthMediator mediator;

    public RegisterPresenter(AuthMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void onCreateCalled() {
        state = new RegisterState();
    }

    @Override
    public void onRecreateCalled() {
        state = mediator.getRegisterState();

        if (state == null) {
            state = new RegisterState();
        }

        view.get().displayRegisterData(state);
    }

    @Override
    public void onPauseCalled(String name, String email, String password) {
        state.name = name;
        state.email = email;
        state.password = password;

        mediator.setRegisterState(state);
    }

    @Override
    public void onDestroyCalled() {

    }

    @Override
    public void registerButtonClicked(String name, String email, String password) {

        state.name = name.trim();
        state.email = email.trim();
        state.password = password.trim();

        if (state.name.isEmpty()) {
            view.get().showMessage("Introduce un nombre");
            return;
        }

        if (state.email.isEmpty()
                || !Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {

            view.get().showMessage("Introduce un email válido");
            return;
        }

        if (state.password.isEmpty() || state.password.length() < 4) {
            view.get().showMessage("La contraseña debe tener al menos 4 caracteres");
            return;
        }

        UserItem user = new UserItem();
        user.name = state.name;
        user.email = state.email;
        user.password = state.password;

        model.registerUser(user, (registeredUser, duplicated, error) -> {

            if (error) {
                view.get().showMessage("Error al registrar usuario");
                return;
            }

            if (duplicated) {
                view.get().showMessage("Ese email ya está registrado");
                return;
            }

            model.saveLoggedUser(registeredUser);
            mediator.setUser(registeredUser);

            view.get().showMessage("Usuario registrado correctamente");
            view.get().navigateToProductListScreen(true);
        });
    }

    @Override
    public void guestButtonClicked() {
        model.clearSession();
        view.get().navigateToProductListScreen(false);
    }

    @Override
    public void loginButtonClicked() {
        view.get().navigateToLoginScreen();
    }

    @Override
    public void injectView(WeakReference<RegisterContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(RegisterContract.Model model) {
        this.model = model;
    }
}