package es.ulpgc.eite.da.advmasterdetail.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.ulpgc.eite.da.advmasterdetail.R;
import es.ulpgc.eite.da.advmasterdetail.games.GameListActivity;

public class LoginActivity
        extends AppCompatActivity implements LoginContract.View {

    private LoginContract.Presenter presenter;

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView guestText, goRegisterText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        LoginScreen.configure(this);

        if (savedInstanceState == null) {
            presenter.onCreateCalled();
        } else {
            presenter.onRecreateCalled();
        }
    }

    private void initView() {
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);

        loginButton = findViewById(R.id.login_button);
        guestText = findViewById(R.id.guest_text);
        goRegisterText = findViewById(R.id.go_register_text);

        loginButton.setOnClickListener(view -> presenter.loginButtonClicked(
                emailEditText.getText().toString(),
                passwordEditText.getText().toString()
        ));

        guestText.setOnClickListener(view -> presenter.guestButtonClicked());

        goRegisterText.setOnClickListener(view -> presenter.registerButtonClicked());
    }

    @Override
    protected void onPause() {
        super.onPause();

        presenter.onPauseCalled(
                emailEditText.getText().toString(),
                passwordEditText.getText().toString()
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroyCalled();
    }

    @Override
    public void displayLoginData(LoginViewModel viewModel) {
        emailEditText.setText(viewModel.email);
        passwordEditText.setText(viewModel.password);
    }

    @Override
    public void showMessage(String message) {
        runOnUiThread(() ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    public void navigateToRegisterScreen() {
        runOnUiThread(() -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void navigateToProductListScreen(boolean loggedIn) {
        runOnUiThread(() -> {
            Intent intent = new Intent(this, GameListActivity.class);
            intent.putExtra("logged_in", loggedIn);
            startActivity(intent);
        });
    }

    @Override
    public void injectPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }
}