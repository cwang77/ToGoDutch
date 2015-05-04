package com.longnightking.togodutch_android.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.longnightking.togodutch_android.MainActivity;
import com.longnightking.togodutch_android.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new LoginRequireCheck().execute();
    }

    private class LoginRequireCheck extends AsyncTask<Void, Void, Intent> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Intent doInBackground(Void... arg0) {
            //check facebook access token
            FacebookSdk.sdkInitialize(getApplicationContext());
            if(AccessToken.getCurrentAccessToken() != null)
                return new Intent(SplashActivity.this, MainActivity.class);
            else
                return new Intent(SplashActivity.this, LoginActivity.class);
        }

        @Override
        protected void onPostExecute(final Intent result) {
            super.onPostExecute(result);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    startActivity(result);
                    finish();
                }
            }, 1000);
        }
    }

}
