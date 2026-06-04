package es.ulpgc.eite.da.advmasterdetail.data;

import java.util.List;

public interface GameRepositoryContract {

    interface FetchGamesDataCallback {
        void onGamesDataFetched(boolean error);
    }

    interface GetGameListCallback {
        void setGameList(List<GameItem> games);
    }

    interface GetGameCallback {
        void setGame(GameItem game);
    }

    interface RegisterUserCallback {
        void onUserRegistered(UserItem user, boolean duplicated, boolean error);
    }

    interface LoginUserCallback {
        void onUserLogged(UserItem user);
    }

    void loadGames(FetchGamesDataCallback callback);

    void getGameList(int userId, GetGameListCallback callback);

    void getFavoriteGameList(int userId, GetGameListCallback callback);

    void getGame(int id, int userId, GetGameCallback callback);

    void updateFavorite(
            int userId,
            int gameId,
            boolean favorite,
            GetGameCallback callback
    );

    void registerUser(UserItem user, RegisterUserCallback callback);

    void loginUser(String email, String password, LoginUserCallback callback);
}