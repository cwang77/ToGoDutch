package com.longnightking.togodutch_android;

import android.app.Activity;
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
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.Toast;

import com.longnightking.togodutch_android.interfaces.OnScrollListener;
import com.longnightking.togodutch_android.widgets.ObservableHorizontalScrollView;
import com.longnightking.togodutch_android.widgets.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private Button rowPlusBtn, colPlusBtn;

    private ViewGroup checkBoxesContainer, horizontalHeader, verticalHeader, tableContainer;

    private ObservableHorizontalScrollView mHorizontalScrollView, horizontalHeaderContainer;

    private ObservableScrollView mVerticalScrollView, verticalHeaderContainer;

    private List<ViewGroup> tableRows;

    private Boolean colorFlag = false;

    private int rowNum = 1, colNum = 1;

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
        checkBoxesContainer = (ViewGroup)findViewById(R.id.checkBoxesContainer);
        horizontalHeader = (ViewGroup)findViewById(R.id.horizontalHeader);
        verticalHeader = (ViewGroup)findViewById(R.id.verticalHeader);
        tableContainer = (ViewGroup)findViewById(R.id.tableContainer);
        mHorizontalScrollView = (ObservableHorizontalScrollView)findViewById(R.id.horizontalScrollBar);
        mVerticalScrollView = (ObservableScrollView)findViewById(R.id.verticalScrollBar);
        verticalHeaderContainer = (ObservableScrollView)findViewById(R.id.verticalHeaderContainer);
        horizontalHeaderContainer = (ObservableHorizontalScrollView)findViewById(R.id.horizontalHeaderContainer);
        TableRow firstRow = new TableRow(this);
        ViewGroup checkBoxInFirstRow = (ViewGroup) LayoutInflater.from(this).inflate(
                R.layout.checkbox_highlight, firstRow, false);
        firstRow.addView(checkBoxInFirstRow);
        checkBoxesContainer.addView(firstRow);

        tableRows = new ArrayList<ViewGroup>();
        bindCheckBox(checkBoxInFirstRow, 0);
        rowPlusBtn.setOnClickListener(mButtonClickListener);
        colPlusBtn.setOnClickListener(mButtonClickListener);
        mHorizontalScrollView.setScrollViewListener(mOnScrollListener);
        mVerticalScrollView.setScrollViewListener(mOnScrollListener);
        verticalHeaderContainer.setOnTouchListener(mTableHeaderOnTouchDoNothingListener);
        horizontalHeaderContainer.setOnTouchListener(mTableHeaderOnTouchDoNothingListener);
        tableRows.add(firstRow);
    }

    private void addRow(){
        int headViewId = R.layout.table_row_subject_matte;
        int cellViewId = R.layout.checkbox_matte;
        if(colorFlag) {
            cellViewId = R.layout.checkbox_highlight;
            headViewId = R.layout.table_row_subject_highlight;
        }
        EditText headerView = (EditText) LayoutInflater.from(this).inflate(
                headViewId, verticalHeader, false);
        TableRow rowView = new TableRow(this);
        for(int i = 0; i < colNum; i++)
        {
            ViewGroup cellView = (ViewGroup) LayoutInflater.from(this).inflate(
                    cellViewId, rowView, false);
            rowView.addView(cellView, i);
            bindCheckBox(cellView, rowNum * 10 + i);

        }
        verticalHeader.addView(headerView, rowNum);
        checkBoxesContainer.addView(rowView, rowNum);
        tableRows.add(rowView);
        colorFlag = !colorFlag;
        rowNum++;
    }

    private void addColumn(){
        ViewGroup headerView = (ViewGroup) LayoutInflater.from(this).inflate(
                R.layout.table_column_subject, horizontalHeader, false);
        horizontalHeader.addView(headerView, colNum);
        colorFlag = true;
        for(int i = 0; i < rowNum; i++){
            int cellViewId = R.layout.checkbox_matte;
            if(colorFlag)
                cellViewId = R.layout.checkbox_highlight;
            ViewGroup cellView = (ViewGroup) LayoutInflater.from(this).inflate(
                    cellViewId, tableRows.get(i), false);
            tableRows.get(i).addView(cellView, colNum);
            bindCheckBox(cellView, i * 10 + colNum);
            colorFlag = !colorFlag;
        }
        colNum++;
    }

    private CompoundButton bindCheckBox(ViewGroup parentView, int id)
    {
        CheckBox mCheckBox = (CheckBox)parentView.findViewById(R.id.checkBox);
        mCheckBox.setOnCheckedChangeListener(mCheckBoxChangeListener);
        mCheckBox.setId(id);
        return mCheckBox;
    }

    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.row_plus_btn:
                    addRow();
                    break;
                case R.id.col_plus_btn:
                    addColumn();
                    break;
                default: break;
            }
        }
    };

    private CheckBox.OnCheckedChangeListener mCheckBoxChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int mId = buttonView.getId();
            if(mId >= 0){
                int mRow = mId / 10;
                int mCol = mId % 10;
                String checkedTxt = "";
                if(isChecked)
                    checkedTxt = "true";
                else
                    checkedTxt = "false";
                Toast.makeText(MainActivity.this, "Checked: " + checkedTxt + ", Row: " + mRow + ", Col: " + mCol, Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(MainActivity.this, "checkbox not exist, mId: " + mId, Toast.LENGTH_SHORT).show();
        }
    };

    private OnScrollListener mOnScrollListener = new OnScrollListener(){
        @Override
        public void onScrollStateChanged(FrameLayout mScrollView, int x, int y, int oldX, int oldY){
            if(mScrollView.getId() == R.id.horizontalScrollBar){
                horizontalHeaderContainer.scrollTo(x, y);
            }else if(mScrollView.getId() == R.id.verticalScrollBar){
                verticalHeaderContainer.scrollTo(x, y);
            }else if(mScrollView.getId() == R.id.verticalHeaderContainer){
                mHorizontalScrollView.scrollTo(x, y);
            }else if (mScrollView.getId() == R.id.horizontalHeaderContainer){
                mVerticalScrollView.scrollTo(x, y);
            }
        }
    };

    private View.OnTouchListener mTableHeaderOnTouchDoNothingListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    };
}
