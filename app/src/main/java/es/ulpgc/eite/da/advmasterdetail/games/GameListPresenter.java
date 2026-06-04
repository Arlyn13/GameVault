package es.ulpgc.eite.da.advmasterdetail.games;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.app.GameMediator;
import es.ulpgc.eite.da.advmasterdetail.data.GameItem;

public class GameListPresenter implements GameListContract.Presenter {

    private WeakReference<GameListContract.View> view;
    private GameListContract.Model model;
    private GameListState state;
    private GameMediator mediator;

    public GameListPresenter(GameMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void onCreateCalled(boolean loggedIn, boolean favoritesMode) {
        state = new GameListState();
        state.loggedIn = loggedIn;
        state.favoritesMode = favoritesMode;
    }

    @Override
    public void onRecreateCalled() {
        state = mediator.getGameListState();

        if (state == null) {
            state = new GameListState();
        }
    }

    @Override
    public void onPauseCalled() {
        mediator.setGameListState(state);
    }

    @Override
    public void fetchGameListData() {

        int userId = model.getLoggedUserId();

        state.loggedIn = userId != -1;

        if (state.favoritesMode) {

            model.fetchFavoriteGameListData(userId, games -> {
                state.games = games;
                view.get().displayGameListData(state);
            });

        } else {

            model.fetchGameListData(userId, games -> {
                state.games = games;
                view.get().displayGameListData(state);
            });
        }
    }

    @Override
    public void selectedGameData(GameItem item) {
        mediator.setGame(item);
        view.get().navigateToGameDetailScreen();
    }

    @Override
    public void injectView(WeakReference<GameListContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(GameListContract.Model model) {
        this.model = model;
    }
}