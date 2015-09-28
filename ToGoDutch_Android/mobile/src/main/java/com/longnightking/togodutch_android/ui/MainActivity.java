package com.longnightking.togodutch_android.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.appevents.AppEventsLogger;
import com.longnightking.togodutch_android.R;
import com.longnightking.togodutch_android.utils.Helper;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends Activity {

    private static final int menuItemsNum = 4;

    private View[] navMenuViews;

    private ImageView[] navMenuIcons;

    private TextView[] navMenuTexts;

    private static final int[] viewIds = {R.id.nav_item_new_statistic, R.id.nav_item_history, R.id.nav_item_unfinished_work, R.id.nav_item_quick_average};

    private static final int[] viewBgColorCodes = {R.color.dashboard_nav_item_new_bg_color, R.color.dashboard_nav_item_history_bg_color, R.color.dashboard_nav_item_unfinished_bg_color, R.color.dashboard_nav_item_quick_avg_bg_color};

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
        if (id == R.id.action_logout) {
            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    Helper.log(TAG, "logout successfully");
                    Helper.redirectTo(MainActivity.this, new Intent(MainActivity.this, AuthActivity.class));
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }

    private void bindViews(){
        navMenuViews = new View[menuItemsNum];
        navMenuIcons = new ImageView[menuItemsNum];
        navMenuTexts = new TextView[menuItemsNum];
        String[] navMenuTxtArray = getResources().getStringArray(R.array.nav_menu_array);
        for(int i = 0; i < menuItemsNum; i++){
            navMenuViews[i] = findViewById(viewIds[i]);
            navMenuViews[i].setBackgroundColor(getResources().getColor(viewBgColorCodes[i]));
            navMenuIcons[i] = (ImageView)navMenuViews[i].findViewById(R.id.menu_item_icon);
            navMenuTexts[i] = (TextView)navMenuViews[i].findViewById(R.id.menu_item_txt);
            navMenuViews[i].setOnClickListener(mButtonClickListener);
            navMenuTexts[i].setText(navMenuTxtArray[i]);
        }
    }

    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            if (v.getId() == viewIds[0]) {
                intent = new Intent(MainActivity.this, StatisticActivity.class);
                intent.putExtra(StatisticActivity.STATISTIC_TABLE_INIT_ROW_NUM, 1);
                intent.putExtra(StatisticActivity.STATISTIC_TABLE_INIT_COL_NUM, 1);
                startActivity(intent);
            }else if (v.getId() == viewIds[1]) {
                intent = new Intent(MainActivity.this, DutchHistoryActivity.class);
                startActivity(intent);
            }else if (v.getId() == viewIds[2]) {
                intent = new Intent(MainActivity.this, UnfinishedWorkActivity.class);
                startActivity(intent);
            }else if (v.getId() == viewIds[3]) {
                intent = new Intent(MainActivity.this, QuickAverageActivity.class);
                startActivity(intent);
            }
        }
    };

}



