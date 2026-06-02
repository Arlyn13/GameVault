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

    void loadGames(FetchGamesDataCallback callback);

    void getGameList(GetGameListCallback callback);

    void getGame(int id, GetGameCallback callback);
}