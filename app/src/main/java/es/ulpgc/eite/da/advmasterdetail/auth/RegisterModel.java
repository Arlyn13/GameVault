package es.ulpgc.eite.da.advmasterdetail.auth;

import es.ulpgc.eite.da.advmasterdetail.app.SessionManager;
import es.ulpgc.eite.da.advmasterdetail.data.GameRepositoryContract;
import es.ulpgc.eite.da.advmasterdetail.data.UserItem;

public class RegisterModel implements RegisterContract.Model {

    private GameRepositoryContract repository;
    private SessionManager sessionManager;

    public RegisterModel(
            GameRepositoryContract repository, SessionManager sessionManager) {

        this.repository = repository;
        this.sessionManager = sessionManager;
    }

    @Override
    public void registerUser(
            UserItem user, GameRepositoryContract.RegisterUserCallback callback) {

        repository.registerUser(user, callback);
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