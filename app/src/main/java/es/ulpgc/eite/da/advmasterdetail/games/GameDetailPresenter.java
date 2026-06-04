package es.ulpgc.eite.da.advmasterdetail.games;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.app.GameMediator;
import es.ulpgc.eite.da.advmasterdetail.data.GameItem;

public class GameDetailPresenter implements GameDetailContract.Presenter {

    private WeakReference<GameDetailContract.View> view;
    private GameDetailContract.Model model;
    private GameDetailState state;
    private GameMediator mediator;

    public GameDetailPresenter(GameMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void onCreateCalled() {
        state = new GameDetailState();

        GameItem game = mediator.getGame();

        if (game != null) {
            state.game = game;
        }

        state.loggedIn = model.getLoggedUserId() != -1;
    }

    @Override
    public void onRecreateCalled() {
        state = mediator.getGameDetailState();

        if (state == null) {
            state = new GameDetailState();
            state.game = mediator.getGame();
        }

        state.loggedIn = model.getLoggedUserId() != -1;
    }

    @Override
    public void onPauseCalled() {
        mediator.setGameDetailState(state);
    }

    @Override
    public void fetchGameDetailData() {
        view.get().displayGameDetailData(state);
    }

    @Override
    public void backButtonClicked() {
        view.get().finishView();
    }



    @Override
    public void injectView(WeakReference<GameDetailContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(GameDetailContract.Model model) {
        this.model = model;
    }
}