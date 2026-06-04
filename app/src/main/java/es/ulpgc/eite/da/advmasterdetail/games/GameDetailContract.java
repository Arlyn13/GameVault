package es.ulpgc.eite.da.advmasterdetail.games;

import java.lang.ref.WeakReference;

public interface GameDetailContract {

    interface View {
        void injectPresenter(Presenter presenter);

        void displayGameDetailData(GameDetailViewModel viewModel);

        void finishView();
    }

    interface Presenter {
        void injectView(WeakReference<View> view);

        void injectModel(Model model);

        void onCreateCalled();

        void onRecreateCalled();

        void onPauseCalled();

        void fetchGameDetailData();

        void backButtonClicked();

        void favoriteButtonClicked();
    }

    interface Model {
        int getLoggedUserId();

        void updateFavorite(
                int userId,
                int gameId,
                boolean favorite,
                es.ulpgc.eite.da.advmasterdetail.data.GameRepositoryContract.GetGameCallback callback
        );
    }
}