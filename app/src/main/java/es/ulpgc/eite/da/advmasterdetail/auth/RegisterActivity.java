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

public class RegisterActivity
        extends AppCompatActivity implements RegisterContract.View {

    private RegisterContract.Presenter presenter;

    private EditText nameEditText, emailEditText, passwordEditText;
    private Button registerButton;
    private TextView guestText, goLoginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        RegisterScreen.configure(this);

        if (savedInstanceState == null) {
            presenter.onCreateCalled();

        } else {
            presenter.onRecreateCalled();
        }
    }

    private void initView() {

        nameEditText = findViewById(R.id.name_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);

        registerButton = findViewById(R.id.register_button);
        guestText = findViewById(R.id.guest_text);
        goLoginText = findViewById(R.id.go_login_text);

        registerButton.setOnClickListener(view -> presenter.registerButtonClicked(
                nameEditText.getText().toString(),
                emailEditText.getText().toString(),
                passwordEditText.getText().toString()
        ));

        guestText.setOnClickListener(view -> presenter.guestButtonClicked());

        goLoginText.setOnClickListener(view -> presenter.loginButtonClicked());
    }

    @Override
    protected void onPause() {
        super.onPause();

        presenter.onPauseCalled(
                nameEditText.getText().toString(),
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
    public void displayRegisterData(RegisterViewModel viewModel) {
        nameEditText.setText(viewModel.name);
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
    public void navigateToLoginScreen() {
        runOnUiThread(() -> {
            Intent intent = new Intent(this, LoginActivity.class);
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
    public void injectPresenter(RegisterContract.Presenter presenter) {
        this.presenter = presenter;
    }
}