package es.ulpgc.eite.da.advmasterdetail.app;

import es.ulpgc.eite.da.advmasterdetail.data.GameItem;
import es.ulpgc.eite.da.advmasterdetail.games.GameDetailState;
import es.ulpgc.eite.da.advmasterdetail.games.GameListState;

public class GameMediator {

    private static GameMediator INSTANCE;

    private GameListState gameListState;
    private GameDetailState gameDetailState;
    private GameItem game;

    private GameMediator() {

    }

    public static void resetInstance() {
        INSTANCE = null;
    }

    public static GameMediator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameMediator();
        }

        return INSTANCE;
    }

    public GameListState getGameListState() {
        return gameListState;
    }

    public void setGameListState(GameListState state) {
        gameListState = state;
    }

    public GameDetailState getGameDetailState() {
        return gameDetailState;
    }

    public void setGameDetailState(GameDetailState state) {
        gameDetailState = state;
    }

    public GameItem getGame() {
        return game;
    }

    public void setGame(GameItem item) {
        game = item;
    }
}