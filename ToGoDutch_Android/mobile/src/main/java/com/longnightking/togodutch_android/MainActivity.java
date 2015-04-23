package com.longnightking.togodutch_android;

import android.app.Activity;
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

import com.longnightking.togodutch_android.statistic.Contact;
import com.longnightking.togodutch_android.statistic.Purchase;
import com.longnightking.togodutch_android.widgets.TableView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends Activity {

    private Button rowPlusBtn, colPlusBtn, calculateBtn;

    private TableView mTable;

    private FrameLayout tableContainer;

    private TextView result;

    private HashMap<Integer, Boolean> statisticMap;

    private HashMap<Integer, Integer> contactsInPurchaseMap;

    public static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        statisticMap = new HashMap<Integer, Boolean>();
        contactsInPurchaseMap = new HashMap<Integer, Integer>();
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
        TableView.TableInitResIds ids = new TableView.TableInitResIds();
        mTable = new TableView(this, ids, 1, 1, mCheckBoxChangeListener);
        tableContainer = (FrameLayout)findViewById(R.id.tableContainer);
        tableContainer.addView(mTable);

        rowPlusBtn = (Button)findViewById(R.id.row_plus_btn);
        colPlusBtn = (Button)findViewById(R.id.col_plus_btn);
        calculateBtn = (Button)findViewById(R.id.calculate_btn);
        rowPlusBtn.setOnClickListener(mButtonClickListener);
        colPlusBtn.setOnClickListener(mButtonClickListener);
        calculateBtn.setOnClickListener(mButtonClickListener);

        result = (TextView)findViewById(R.id.calculateResult);
    }

    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.row_plus_btn:
                    mTable.addRow();
                    break;
                case R.id.col_plus_btn:
                    mTable.addColumn();
                    break;
                case R.id.calculate_btn:
                    calculateDutch();
                default: break;
            }
        }
    };

    private void calculateDutch(){
        List<Contact> mContactList = new ArrayList<Contact>();
        List<Purchase> mPurchaseList = new ArrayList<Purchase>();
        boolean scanResult = mTable.getStatsDataFromUI(mContactList, mPurchaseList);
        if(scanResult)
        {
            int cNum = mPurchaseList.size();
            int rNum = mContactList.size();
            for(int c = 0; c < cNum; c++){
                int sharedContactsCount = contactsInPurchaseMap.get(c);
                if(sharedContactsCount <= 0)
                {
                    Toast.makeText(MainActivity.this, "No one paid for purchase NO." + c + ".", Toast.LENGTH_SHORT).show();
                    return;
                }
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
            result.setText(resultTxt);
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
                statisticMap.put(mId, isChecked);
                Log.i(TAG, "Checked: " + checkedTxt + ", Row: " + mRow + ", Col: " + mCol);
            }
            else
                Log.i(TAG, "checkbox not exist, mId: " + mId);
        }
    };
}



