package com.longnightking.togodutch_android.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.login.widget.LoginButton;
import com.longnightking.togodutch_android.R;
import com.longnightking.togodutch_android.global.UserManagement;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

/**
 * A login screen that offers login via email/password and via Facebook and wechat sign in.
 * <p/>
 */
public class LoginActivity extends Activity {
    private LoginButton faceBookLoginBtn;

    public static final String TAG = LoginActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    private void bindViews(){
        faceBookLoginBtn = (LoginButton)findViewById(R.id.fb_login_button);

        faceBookLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseFacebookUtils.logInWithReadPermissionsInBackground(LoginActivity.this, UserManagement.getInstance(LoginActivity.this).getPermissionsList(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        if (user == null) {
                            Log.d(TAG, "Uh oh. The user cancelled the Facebook login.");
                        } else {
                            if (user.isNew()) {
                                Log.d(TAG, "User signed up and logged in through Facebook!");
                            } else {
                                Log.d(TAG, "User logged in through Facebook!");
                            }
                            reDirectToMainActivity();
                        }
                    }
                });
            }
        });
    }

    private void reDirectToMainActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}