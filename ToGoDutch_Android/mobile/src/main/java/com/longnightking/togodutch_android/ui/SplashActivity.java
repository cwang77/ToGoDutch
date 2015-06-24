package com.longnightking.togodutch_android.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.longnightking.togodutch_android.R;
import com.longnightking.togodutch_android.global.UserManagement;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        new LoginRequireCheck().execute();
        final Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 500);
    }

    private class LoginRequireCheck extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ParseFacebookUtils.initialize(SplashActivity.this);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            final ParseUser currentUser = ParseUser.getCurrentUser();
            if(currentUser != null && !ParseFacebookUtils.isLinked(currentUser))
                ParseFacebookUtils.linkWithReadPermissionsInBackground(currentUser, SplashActivity.this, UserManagement.getInstance(SplashActivity.this).getPermissionsList(), new SaveCallback() {
                    @Override
                    public void done(ParseException ex) {
                        if (ParseFacebookUtils.isLinked(currentUser)) {
                            Log.d("SplashActivity", "Woohoo, user logged in with Facebook!");
                        }
                    }
                });
            return null;
        }

        @Override
        protected void onPostExecute(final Void result) {
            super.onPostExecute(result);
            final Intent intent;
            if(ParseUser.getCurrentUser() == null)
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            else
                intent = new Intent(SplashActivity.this, MainActivity.class);

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    startActivity(intent);
                    finish();
                }
            }, 500);
        }
    }

}
