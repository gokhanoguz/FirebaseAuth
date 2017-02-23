package com.javatar.firebaseauth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private Button registerButton;
    private Button loginButton;
    private Button forgotPasswordButton;
    private EditText emailEditText;
    private EditText passwordEditText;
    private FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseHelper = new FirebaseHelper(this);
        if(firebaseHelper.isAuthenticated()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        initUI();
    }

    private void initUI() {
        emailEditText = (EditText) findViewById(R.id.email);
        passwordEditText = (EditText) findViewById(R.id.password);
        registerButton = (Button) findViewById(R.id.register_button);
        loginButton = (Button) findViewById(R.id.login_button);
        forgotPasswordButton = (Button) findViewById(R.id.forgot_password_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                final String password = passwordEditText.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    emailEditText.setError(getString(R.string.enter_your_email));
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    passwordEditText.setError(getString(R.string.enter_your_password));
                    return;
                }

                firebaseHelper.doAuthenticate(email, password);
            }
        });
    }


}
