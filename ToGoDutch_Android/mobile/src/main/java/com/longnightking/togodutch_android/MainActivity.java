package com.longnightking.togodutch_android;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.longnightking.togodutch_android.interfaces.OnScrollListener;
import com.longnightking.togodutch_android.widgets.ObservableHorizontalScrollView;
import com.longnightking.togodutch_android.widgets.ObservableScrollView;
import com.longnightking.togodutch_android.widgets.TableView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private Button rowPlusBtn, colPlusBtn;

    private TableView mTable;

    private FrameLayout tableContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void bindViews(){
        rowPlusBtn = (Button)findViewById(R.id.row_plus_btn);
        colPlusBtn = (Button)findViewById(R.id.col_plus_btn);
        TableView.TableInitResIds ids = new TableView.TableInitResIds();
        mTable = new TableView(this, ids, 5, 5);
        tableContainer = (FrameLayout)findViewById(R.id.tableContainer);
        tableContainer.addView(mTable);
        rowPlusBtn.setOnClickListener(mButtonClickListener);
        colPlusBtn.setOnClickListener(mButtonClickListener);
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
                default: break;
            }
        }
    };
}



