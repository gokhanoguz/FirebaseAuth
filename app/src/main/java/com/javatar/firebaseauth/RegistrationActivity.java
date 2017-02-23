package com.javatar.firebaseauth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity {

    private Button registerButton;
    private Button loginButton;
    private Button forgotPasswordButton;
    private EditText emailEditText;
    private EditText passwordEditText;
    private FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firebaseHelper = new FirebaseHelper(this);

        initUI();
    }

    private void initUI() {
        loginButton = (Button) findViewById(R.id.login_button);
        registerButton = (Button) findViewById(R.id.register_button);
        emailEditText = (EditText) findViewById(R.id.email);
        passwordEditText = (EditText) findViewById(R.id.password);
        forgotPasswordButton = (Button) findViewById(R.id.forgot_password_button);

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, ResetPasswordActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    emailEditText.setError(getString(R.string.enter_your_email));
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    passwordEditText.setError(getString(R.string.enter_your_password));
                    return;
                }
                if (password.length() < 6) {
                    passwordEditText.setError(getString(R.string.minimum_password));
                    return;
                }
                firebaseHelper.doRegister(email, password);
            }
        });
    }

}