package com.javatar.firebaseauth;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener {

    private Button changeEmailButton;
    private Button changePasswordButton;
    private Button sendResetEmailButton;
    private Button removeUserButton;
    private Button signOutButton;

    private FirebaseHelper firebaseHelper;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseHelper = new FirebaseHelper(this);
        user = FirebaseHelper.getUser();
        initUI();
    }

    private void initUI() {

        changeEmailButton = (Button) findViewById(R.id.change_email_button);
        changePasswordButton = (Button) findViewById(R.id.change_password_button);
        sendResetEmailButton = (Button) findViewById(R.id.sending_pass_reset_button);
        removeUserButton = (Button) findViewById(R.id.remove_user_button);
        signOutButton = (Button) findViewById(R.id.sign_out);


        changeEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final EditText edittext = new EditText(MainActivity.this);
                builder.setTitle(getString(R.string.enter_new_email));
                builder.setView(edittext);
                builder.setPositiveButton(getString(R.string.change), null);
                builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (user != null && !edittext.getText().toString().trim().equals("")) {
                            firebaseHelper.updateEmail(edittext.getText().toString().trim());
                        } else if (edittext.getText().toString().trim().equals("")) {
                            edittext.setError(getString(R.string.enter_new_email));
                        }
                    }
                });
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final EditText edittext = new EditText(MainActivity.this);
                builder.setTitle(getString(R.string.enter_new_password));
                builder.setView(edittext);
                builder.setPositiveButton(getString(R.string.change), null);
                builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (user != null && !edittext.getText().toString().trim().equals("")) {
                            if (edittext.getText().toString().trim().length() < 6) {
                                edittext.setError(getString(R.string.minimum_password));
                            } else {
                                firebaseHelper.updatePassword(edittext.getText().toString().trim());
                            }
                        } else if (edittext.getText().toString().trim().equals("")) {
                            edittext.setError(getString(R.string.enter_new_password));
                        }
                    }
                });

            }
        });

        sendResetEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final EditText edittext = new EditText(MainActivity.this);
                builder.setTitle(getString(R.string.enter_your_email));
                builder.setView(edittext);
                builder.setPositiveButton(getString(R.string.send), null);
                builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!edittext.getText().toString().trim().equals("")) {
                            firebaseHelper.resetMyPassword(edittext.getText().toString().trim());
                            dialog.dismiss();
                        } else {
                            edittext.setError(getString(R.string.enter_your_email));
                        }
                    }
                });
            }
        });

        removeUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    firebaseHelper.deleteUser();
                }
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseHelper.signOut();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        if (firebaseHelper != null) {
            firebaseHelper.addAuthStateListener(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (firebaseHelper != null) {
            firebaseHelper.removeAuthStateListener(this);
        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (firebaseHelper != null && firebaseHelper.getUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }
}