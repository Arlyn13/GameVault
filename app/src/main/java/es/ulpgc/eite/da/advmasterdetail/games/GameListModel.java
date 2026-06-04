package es.ulpgc.eite.da.advmasterdetail.games;

import es.ulpgc.eite.da.advmasterdetail.app.SessionManager;
import es.ulpgc.eite.da.advmasterdetail.data.GameRepositoryContract;

public class GameListModel implements GameListContract.Model {

    private GameRepositoryContract repository;
    private SessionManager sessionManager;

    public GameListModel(
            GameRepositoryContract repository,
            SessionManager sessionManager) {

        this.repository = repository;
        this.sessionManager = sessionManager;
    }

    @Override
    public void fetchGameListData(
            int userId,
            GameRepositoryContract.GetGameListCallback callback) {

        repository.loadGames(error -> {
            if (!error) {
                repository.getGameList(userId, callback);
            }
        });
    }

    @Override
    public void fetchFavoriteGameListData(
            int userId,
            GameRepositoryContract.GetGameListCallback callback) {

        repository.loadGames(error -> {
            if (!error) {
                repository.getFavoriteGameList(userId, callback);
            }
        });
    }

    @Override
    public void updateFavorite(
            int userId,
            int gameId,
            boolean favorite,
            GameRepositoryContract.GetGameCallback callback) {

        repository.updateFavorite(userId, gameId, favorite, callback);
    }

    @Override
    public int getLoggedUserId() {
        if (sessionManager.isLoggedIn()) {
            return sessionManager.getUserId();
        }

        return -1;
    }
}