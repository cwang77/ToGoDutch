package com.longnightking.togodutch_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.longnightking.togodutch_android.ui.DutchHistoryActivity;
import com.longnightking.togodutch_android.ui.QuickAverageActivity;
import com.longnightking.togodutch_android.ui.StatisticActivity;
import com.longnightking.togodutch_android.ui.UnfinishedWorkActivity;

public class MainActivity extends Activity {

    private Button navBtnNew, navBtnHistory, navBtnUnfinished, navBtnQuickAverage;

    public static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void bindViews(){
        navBtnNew = (Button)findViewById(R.id.nav_btn_new_statistic);
        navBtnHistory = (Button)findViewById(R.id.nav_btn_history);
        navBtnUnfinished = (Button)findViewById(R.id.nav_btn_unfinished_work);
        navBtnQuickAverage = (Button)findViewById(R.id.nav_btn_quick_average);
        navBtnNew.setOnClickListener(mButtonClickListener);
        navBtnHistory.setOnClickListener(mButtonClickListener);
        navBtnUnfinished.setOnClickListener(mButtonClickListener);
        navBtnQuickAverage.setOnClickListener(mButtonClickListener);
    }

    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch(v.getId()){
                case R.id.nav_btn_new_statistic:
                    intent = new Intent(MainActivity.this, StatisticActivity.class);
                    intent.putExtra(StatisticActivity.STATISTIC_TABLE_INIT_ROW_NUM, 1);
                    intent.putExtra(StatisticActivity.STATISTIC_TABLE_INIT_COL_NUM, 1);
                    startActivity(intent);
                    break;
                case R.id.nav_btn_history:
                    intent = new Intent(MainActivity.this, DutchHistoryActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_btn_unfinished_work:
                    intent = new Intent(MainActivity.this, UnfinishedWorkActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_btn_quick_average:
                    intent = new Intent(MainActivity.this, QuickAverageActivity.class);
                    startActivity(intent);
                    break;
                default: break;
            }
        }
    };

}



