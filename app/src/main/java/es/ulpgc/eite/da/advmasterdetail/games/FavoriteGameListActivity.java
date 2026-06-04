package es.ulpgc.eite.da.advmasterdetail.games;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.ulpgc.eite.da.advmasterdetail.R;
import es.ulpgc.eite.da.advmasterdetail.data.GameItem;

public class FavoriteGameListActivity
        extends AppCompatActivity implements GameListContract.View {

    private GameListContract.Presenter presenter;
    private GameListAdapter listAdapter;

    private TextView titleView;
    private TextView favoritesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        GameListScreen.configure(this);

        initGameListContainer();

        if (savedInstanceState == null) {
            presenter.onCreateCalled(true, true);

        } else {
            presenter.onRecreateCalled();
        }
    }

    private void initGameListContainer() {

        titleView = findViewById(R.id.product_list_title);
        favoritesButton = findViewById(R.id.favorites_button);

        titleView.setText("Favoritos");
        favoritesButton.setVisibility(View.GONE);

        listAdapter = new GameListAdapter(
                view -> {
                    GameItem item = (GameItem) view.getTag();
                    presenter.selectedGameData(item);
                },
                view -> {
                    GameItem item = (GameItem) view.getTag();
                    presenter.favoriteButtonClicked(item);
                }
        );

        RecyclerView recyclerView = findViewById(R.id.product_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.fetchGameListData();
    }

    @Override
    protected void onPause() {
        super.onPause();

        presenter.onPauseCalled();
    }

    @Override
    public void displayGameListData(GameListViewModel viewModel) {

        runOnUiThread(() -> {
            titleView.setText("Favoritos");
            favoritesButton.setVisibility(View.GONE);

            listAdapter.setCanFavorite(true);
            listAdapter.setItems(viewModel.games);
        });
    }

    @Override
    public void navigateToGameDetailScreen() {
        Intent intent = new Intent(this, GameDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void injectPresenter(GameListContract.Presenter presenter) {
        this.presenter = presenter;
    }
}