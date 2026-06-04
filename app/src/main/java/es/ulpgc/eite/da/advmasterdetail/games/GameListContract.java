package es.ulpgc.eite.da.advmasterdetail.games;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.data.GameItem;
import es.ulpgc.eite.da.advmasterdetail.data.GameRepositoryContract;

public interface GameListContract {

    interface View {
        void injectPresenter(Presenter presenter);

        void displayGameListData(GameListViewModel viewModel);

        void navigateToGameDetailScreen();
    }

    interface Presenter {
        void injectView(WeakReference<View> view);

        void injectModel(Model model);

        void onCreateCalled(boolean loggedIn, boolean favoritesMode);

        void onRecreateCalled();

        void onPauseCalled();

        void fetchGameListData();

        void selectedGameData(GameItem item);

        void favoriteButtonClicked(GameItem item);
    }

    interface Model {
        void fetchGameListData(
                int userId,
                GameRepositoryContract.GetGameListCallback callback);

        void fetchFavoriteGameListData(
                int userId,
                GameRepositoryContract.GetGameListCallback callback);

        void updateFavorite(
                int userId,
                int gameId,
                boolean favorite,
                GameRepositoryContract.GetGameCallback callback);

        int getLoggedUserId();
    }
}