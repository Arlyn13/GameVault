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
    private TextView favoriteButton;

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
        favoriteButton = findViewById(R.id.favorite_button);

        backButton.setOnClickListener(view -> presenter.backButtonClicked());
        favoriteButton.setOnClickListener(view -> presenter.favoriteButtonClicked());
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
                    .setText("GÉNERO\n" + game.genre);

            ((TextView) findViewById(R.id.product_platform))
                    .setText("PLATAFORMA\n" + game.platform);

            ((TextView) findViewById(R.id.product_year))
                    .setText("AÑO DE LANZAMIENTO\n" + game.year);

            ((TextView) findViewById(R.id.product_developer))
                    .setText("DESARROLLADORA\n" + game.developer);

            ((TextView) findViewById(R.id.product_likes))
                    .setText("ME GUSTA\n♥ " + game.totalFavorites);

            ((TextView) findViewById(R.id.product_detail))
                    .setText(game.description);

            Glide.with(this)
                    .load(game.image)
                    .into((ImageView) findViewById(R.id.product_image));

            if (viewModel.loggedIn) {
                favoriteButton.setVisibility(View.VISIBLE);

                if (game.favorite) {
                    favoriteButton.setText("♥");
                    favoriteButton.setTextColor(
                            ContextCompat.getColor(this, R.color.gv_red)
                    );

                } else {
                    favoriteButton.setText("♡");
                    favoriteButton.setTextColor(
                            ContextCompat.getColor(this, R.color.gv_white)
                    );
                }

            } else {
                favoriteButton.setVisibility(View.GONE);
            }
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