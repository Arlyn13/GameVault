package es.ulpgc.eite.da.advmasterdetail.games;

import es.ulpgc.eite.da.advmasterdetail.app.SessionManager;
import es.ulpgc.eite.da.advmasterdetail.data.GameRepositoryContract;

public class GameDetailModel implements GameDetailContract.Model {

    private SessionManager sessionManager;
    private GameRepositoryContract repository;

    public GameDetailModel(
            GameRepositoryContract repository,
            SessionManager sessionManager) {

        this.repository = repository;
        this.sessionManager = sessionManager;
    }

    @Override
    public int getLoggedUserId() {
        if (sessionManager.isLoggedIn()) {
            return sessionManager.getUserId();
        }

        return -1;
    }

    @Override
    public void updateFavorite(
            int userId,
            int gameId,
            boolean favorite,
            GameRepositoryContract.GetGameCallback callback) {

        repository.updateFavorite(userId, gameId, favorite, callback);
    }
}