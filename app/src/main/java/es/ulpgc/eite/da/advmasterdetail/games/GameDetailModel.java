package es.ulpgc.eite.da.advmasterdetail.games;

import es.ulpgc.eite.da.advmasterdetail.app.SessionManager;

public class GameDetailModel implements GameDetailContract.Model {

    private SessionManager sessionManager;

    public GameDetailModel(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public int getLoggedUserId() {
        if (sessionManager.isLoggedIn()) {
            return sessionManager.getUserId();
        }

        return -1;
    }
}