package com.javatar.firebaseauth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button resetMyPasswordButton;
    private Button backButton;
    private FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        firebaseHelper = new FirebaseHelper(this);
        initUI();
    }

    private void initUI() {
        emailEditText = (EditText) findViewById(R.id.email);
        resetMyPasswordButton = (Button) findViewById(R.id.reset_my_password_button);
        backButton = (Button) findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        resetMyPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    emailEditText.setError(getString(R.string.enter_your_email));
                    return;
                }
                firebaseHelper.resetMyPassword(email);
            }
        });
    }

}
