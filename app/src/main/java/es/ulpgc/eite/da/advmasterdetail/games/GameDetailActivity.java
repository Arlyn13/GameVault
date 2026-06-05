package es.ulpgc.eite.da.advmasterdetail.games;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import es.ulpgc.eite.da.advmasterdetail.R;
import es.ulpgc.eite.da.advmasterdetail.data.GameItem;

public class GameDetailActivity
        extends AppCompatActivity implements GameDetailContract.View {

    private GameDetailContract.Presenter presenter;

    private TextView backButton;
    private TextView likesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        initView();

        GameDetailScreen.configure(this);

        if (savedInstanceState == null) {
            presenter.onCreateCalled();
        } else {
            presenter.onRecreateCalled();
        }
    }

    private void initView() {
        backButton = findViewById(R.id.back_button);
        likesView = findViewById(R.id.product_likes);

        backButton.setOnClickListener(view -> presenter.backButtonClicked());

        likesView.setOnClickListener(view -> presenter.favoriteButtonClicked());
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.fetchGameDetailData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPauseCalled();
    }

    @Override
    public void displayGameDetailData(GameDetailViewModel viewModel) {

        runOnUiThread(() -> {

            GameItem game = viewModel.game;

            if (game == null) {
                return;
            }

            ((TextView) findViewById(R.id.product_name))
                    .setText(game.title);

            ((TextView) findViewById(R.id.product_genre))
                    .setText(game.genre);

            ((TextView) findViewById(R.id.product_platform))
                    .setText(game.platform);

            ((TextView) findViewById(R.id.product_year))
                    .setText(String.valueOf(game.year));

            ((TextView) findViewById(R.id.product_developer))
                    .setText(game.developer);

            likesView.setText("♥\uFE0E " + game.totalFavorites);

            if (viewModel.loggedIn) {
                likesView.setVisibility(View.VISIBLE);
                likesView.setClickable(true);

                if (game.favorite) {
                    likesView.setTextColor(
                            ContextCompat.getColor(this, R.color.gv_red)
                    );
                } else {
                    likesView.setTextColor(
                            ContextCompat.getColor(this, R.color.gv_text_gray)
                    );
                }

            } else {
                likesView.setVisibility(View.VISIBLE);
                likesView.setClickable(false);
                likesView.setTextColor(
                        ContextCompat.getColor(this, R.color.gv_text_gray)
                );
            }

            ((TextView) findViewById(R.id.product_detail))
                    .setText(game.description);

            Glide.with(this)
                    .load(game.image)
                    .into((ImageView) findViewById(R.id.product_image));
        });
    }

    @Override
    public void finishView() {
        finish();
    }

    @Override
    public void injectPresenter(GameDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }
}