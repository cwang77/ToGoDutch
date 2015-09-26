package com.longnightking.togodutch_android.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.longnightking.togodutch_android.R;
import com.longnightking.togodutch_android.global.UserManagement;
import com.longnightking.togodutch_android.utils.Helper;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class SplashActivity extends Activity {

    private static String TAG = "SplashActivity";

    private final int REDIRECT_BUFFER_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new LoginRequireCheck().execute();
    }

    private class LoginRequireCheck extends AsyncTask<Void, Void, Void> {

        private Intent redirectIntent = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            final ParseUser currentUser = ParseUser.getCurrentUser();
            if(currentUser != null){
                redirectIntent = new Intent(SplashActivity.this, MainActivity.class);
                Helper.log(TAG, "already logged in, goes into main menu");
            }else{
                redirectIntent = new Intent(SplashActivity.this, AuthActivity.class);
                Helper.log(TAG, "login required, goes into login page");
            }
            return null;
        }

        @Override
        protected void onPostExecute(final Void result) {
            super.onPostExecute(result);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Helper.redirectTo(SplashActivity.this, redirectIntent);
                }
            }, REDIRECT_BUFFER_TIME);
        }
    }

}
