package com.javatar.firebaseauth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by gokhan on 22-Feb-17.
 */

public class FirebaseHelper {

    private Context mContext;
    private FirebaseAuth mFirebaseAuth;

    public FirebaseHelper(Context context) {
        this.mContext = context;
        mFirebaseAuth = getFirebaseAuth();
    }

    private FirebaseAuth getFirebaseAuth() {
        if (mFirebaseAuth == null) {
            mFirebaseAuth = FirebaseAuth.getInstance();
        }
        return mFirebaseAuth;
    }

    public void doAuthenticate(String email, String password) {

        getFirebaseAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, mContext.getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(mContext, MainActivity.class);
                            mContext.startActivity(intent);
                            ((Activity) mContext).finish();
                        }
                    }
                });
    }

    public void doRegister(String email, String password) {

        getFirebaseAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText((Activity) mContext, mContext.getString(R.string.registration_fail),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, mContext.getString(R.string.registration_success), Toast.LENGTH_SHORT).show();
                            mContext.startActivity(new Intent(mContext, MainActivity.class));
                            ((Activity) mContext).finish();
                        }
                    }
                });
    }

    public boolean isAuthenticated() {
        if (getUser() != null) {
            return true;
        }
        return false;
    }

    public static FirebaseUser getUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public void resetMyPassword(String email) {
        mFirebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, mContext.getString(R.string.send_reset_mail_fail), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, mContext.getString(R.string.send_reset_mail_success), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signOut() {
        if(getUser() == null) {
            return;
        }
        mFirebaseAuth.signOut();
    }

    public void addAuthStateListener(FirebaseAuth.AuthStateListener listener) {
        mFirebaseAuth.addAuthStateListener(listener);
    }

    public void removeAuthStateListener(FirebaseAuth.AuthStateListener listener) {
        mFirebaseAuth.removeAuthStateListener(listener);
    }

    public void updateEmail(String email) {
        if (getUser() == null) {
            return;
        }
        getUser().updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(mContext, mContext.getString(R.string.email_update_success), Toast.LENGTH_LONG).show();
                            signOut();
                        } else {
                            Toast.makeText(mContext, mContext.getString(R.string.email_update_fail), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void updatePassword(String password) {
        if (getUser() == null) {
            return;
        }
        getUser().updatePassword(password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(mContext, mContext.getString(R.string.password_update_success), Toast.LENGTH_SHORT).show();
                            signOut();
                        } else {
                            Toast.makeText(mContext, mContext.getString(R.string.password_update_fail), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void deleteUser() {
        if (getUser() == null) {
            return;
        }
        getUser().delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(mContext, mContext.getString(R.string.account_delete_success), Toast.LENGTH_SHORT).show();
                            mContext.startActivity(new Intent(mContext, RegistrationActivity.class));
                            ((Activity) mContext).finish();
                        } else {
                            Toast.makeText(mContext, mContext.getString(R.string.account_delete_fail), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
