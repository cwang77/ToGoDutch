package com.longnightking.togodutch_android.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.longnightking.togodutch_android.R;
import com.longnightking.togodutch_android.interfaces.OnDialogSelectionListener;
import com.longnightking.togodutch_android.statistic.Contact;
import com.longnightking.togodutch_android.statistic.Purchase;
import com.longnightking.togodutch_android.utils.Helper;
import com.longnightking.togodutch_android.widgets.TableView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class StatisticActivity extends Activity {

    private SlidingUpPanelLayout mSlidingPanel;

    private View insertRowBtn, insertColumnBtn;

    private TableView mTable;

    private FrameLayout tableContainer;

    private HashMap<Integer, Boolean> statisticMap;

    private HashMap<Integer, Integer> contactsInPurchaseMap;

    public static final String TAG = StatisticActivity.class.getName();

    public static final String STATISTIC_TABLE_INIT_ROW_NUM = "com.longnightking.togodutch_android.ui.StatisticActivity.init_row_num";

    public static final String STATISTIC_TABLE_INIT_COL_NUM = "com.longnightking.togodutch_android.ui.StatisticActivity.init_col_num";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_statistic);
        bindViews(intent.getIntExtra(STATISTIC_TABLE_INIT_ROW_NUM, 1), intent.getIntExtra(STATISTIC_TABLE_INIT_COL_NUM, 1));
        statisticMap = new HashMap<Integer, Boolean>();
        contactsInPurchaseMap = new HashMap<Integer, Integer>();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        //savedInstanceState.putInt(STATE_SCORE, mCurrentScore);
        //savedInstanceState.putInt(STATE_LEVEL, mCurrentLevel);

        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_statistic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            return true;
        } else if(id == R.id.action_insert) {
            mSlidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else if(id == R.id.action_clear){
            Helper.showOkOrCancelPopupDialog(this,"All the editted infomation will be clear", "Confirm Clear", clearConfimrDialogListener);
        }else if(id == R.id.action_check_out){
            calculateDutch();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mSlidingPanel != null && mSlidingPanel.getPanelState() !=SlidingUpPanelLayout.PanelState.HIDDEN){
                mSlidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        } else {
            super.onBackPressed();
        }
    }

    private void bindViews(int initRowNum, int initColNum){
        TableView.TableInitResIds ids = new TableView.TableInitResIds();
        mTable = new TableView(this, ids, initRowNum, initColNum, mCheckBoxChangeListener);
        tableContainer = (FrameLayout)findViewById(R.id.tableContainer);
        tableContainer.addView(mTable);

        mSlidingPanel = (SlidingUpPanelLayout)findViewById(R.id.sliding_panel_container);
        mSlidingPanel.setPanelSlideListener(mPanelSlideListener);
        mSlidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        insertRowBtn = findViewById(R.id.insert_row_btn);
        insertColumnBtn = findViewById(R.id.insert_column_btn);
        insertRowBtn.setOnClickListener(actionItemOnClickListener);
        insertColumnBtn.setOnClickListener(actionItemOnClickListener);
    }

    private void calculateDutch(){
        List<Contact> mContactList = new ArrayList<Contact>();
        List<Purchase> mPurchaseList = new ArrayList<Purchase>();
        boolean scanResult = mTable.getStatsDataFromUI(mContactList, mPurchaseList);
        if(scanResult)
        {
            int cNum = mPurchaseList.size();
            int rNum = mContactList.size();
            for(int c = 0; c < cNum; c++){
                if(mTable.getPurchaseTakenTimes(c) <= 0){
                    Toast.makeText(StatisticActivity.this, "No one paid for purchase NO." + (c+1) + ".", Toast.LENGTH_SHORT).show();
                    return;
                }
                int sharedContactsCount = contactsInPurchaseMap.get(c);
                double allocatedCost = mPurchaseList.get(c).getConsumption() / sharedContactsCount;
                for(int r = 0; r < rNum; r++){
                    int hashKey = r * 10 + c;
                    if(statisticMap.containsKey(hashKey)){
                        if(statisticMap.get(hashKey)){
                            mContactList.get(r).addPayment(allocatedCost);
                        }
                    }
                }
            }

            String resultTxt = "";
            for(int i = 0; i < rNum; i++)
                resultTxt += "Name: " + mContactList.get(i).getContactName() + ", Pay: " + mContactList.get(i).getPayment() + "\n";
            Toast.makeText(this, resultTxt, Toast.LENGTH_LONG).show();
        }

    }

    private CheckBox.OnCheckedChangeListener mCheckBoxChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int mId = buttonView.getId();
            if(mId >= 0){
                int mRow = mId / 10;
                int mCol = mId % 10;
                String checkedTxt = "";
                if(!contactsInPurchaseMap.containsKey(mCol))
                    contactsInPurchaseMap.put(mCol, 0);
                if(isChecked) {
                    contactsInPurchaseMap.put(mCol, contactsInPurchaseMap.get(mCol) + 1);
                    checkedTxt = "true";
                }
                else {
                    contactsInPurchaseMap.put(mCol, contactsInPurchaseMap.get(mCol) - 1);
                    checkedTxt = "false";
                }
                mTable.markPurchaseHasBeenTaken(mCol, isChecked);
                statisticMap.put(mId, isChecked);
                Log.i(TAG, "Checked: " + checkedTxt + ", Row: " + mRow + ", Col: " + mCol);
            }
            else
                Log.i(TAG, "checkbox not exist, mId: " + mId);
        }
    };

    private SlidingUpPanelLayout.PanelSlideListener mPanelSlideListener = new SlidingUpPanelLayout.PanelSlideListener() {
        @Override
        public void onPanelSlide(View panel, float slideOffset) {
            Log.i(TAG, "onPanelSlide, offset " + slideOffset);
        }

        @Override
        public void onPanelExpanded(View panel) {
            Log.i(TAG, "onPanelExpanded");

        }

        @Override
        public void onPanelCollapsed(View panel) {
            Log.i(TAG, "onPanelCollapsed");

        }

        @Override
        public void onPanelAnchored(View panel) {
            Log.i(TAG, "onPanelAnchored");
        }

        @Override
        public void onPanelHidden(View panel) {
            Log.i(TAG, "onPanelHidden");
        }
    };

    private View.OnClickListener actionItemOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            if(v.getId() == R.id.insert_column_btn) {
                mTable.addColumn();
            } else if(v.getId() == R.id.insert_row_btn) {
                mTable.addRow();
            }
            mSlidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        }
    };

    private OnDialogSelectionListener clearConfimrDialogListener = new OnDialogSelectionListener(){
        @Override
        public void onPositiveSelected(){
            //clear data
        }
        @Override
        public void onNegativeSelected(){
            //do nothing
        }
    };
}
