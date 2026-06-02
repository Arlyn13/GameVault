package es.ulpgc.eite.da.advmasterdetail.auth;

import es.ulpgc.eite.da.advmasterdetail.app.SessionManager;
import es.ulpgc.eite.da.advmasterdetail.data.GameRepositoryContract;
import es.ulpgc.eite.da.advmasterdetail.data.UserItem;

public class LoginModel implements LoginContract.Model {

    private GameRepositoryContract repository;
    private SessionManager sessionManager;

    public LoginModel(
            GameRepositoryContract repository, SessionManager sessionManager) {

        this.repository = repository;
        this.sessionManager = sessionManager;
    }

    @Override
    public void loginUser(
            String email,
            String password,
            GameRepositoryContract.LoginUserCallback callback) {

        repository.loginUser(email, password, callback);
    }

    @Override
    public void saveLoggedUser(UserItem user) {
        sessionManager.saveLoggedUser(user);
    }

    @Override
    public void clearSession() {
        sessionManager.clearSession();
    }
}