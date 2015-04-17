package com.longnightking.togodutch_android;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private Button rowPlusBtn, colPlusBtn;

    private ViewGroup statsTable, firstRow, secondRow;

    private List<ViewGroup> tableRows;

    private Boolean rowColorFlag = false, colColorFlag = false;

    private int rowNum = 2, colNum = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
        statsTable = (ViewGroup)findViewById(R.id.stats_table);
        firstRow = (ViewGroup)findViewById(R.id.table_1st_row);
        secondRow = (ViewGroup)findViewById(R.id.table_2nd_row);
        tableRows = new ArrayList<ViewGroup>();
        rowPlusBtn.setOnClickListener(mButtonClickListener);
        colPlusBtn.setOnClickListener(mButtonClickListener);
    }

    private void addRow(){
        int rowViewId = R.layout.table_row_subject_matte;
        int cellViewId = R.layout.checkbox_matte;
        colColorFlag = rowColorFlag;
        if(rowColorFlag)
            rowViewId = R.layout.table_row_subject_highlight;
        if(colColorFlag)
            cellViewId = R.layout.checkbox_highlight;
        ViewGroup rowView = (ViewGroup) LayoutInflater.from(this).inflate(
                rowViewId, statsTable, false);
        for(int i = 2; i < colNum; i++)
        {
            ViewGroup cellView = (ViewGroup) LayoutInflater.from(this).inflate(
                    cellViewId, rowView, false);
            rowView.addView(cellView, i);
        }
        statsTable.addView(rowView, rowNum);
        tableRows.add(rowView);
        rowColorFlag = !rowColorFlag;
        colColorFlag = rowColorFlag;
        rowNum++;
    }

    private void addColumn(){
        ViewGroup firstColView = (ViewGroup) LayoutInflater.from(this).inflate(
                R.layout.table_column_subject, firstRow, false);
        firstRow.addView(firstColView, colNum);
        ViewGroup secondColView = (ViewGroup) LayoutInflater.from(this).inflate(
                R.layout.checkbox_highlight, secondRow, false);
        secondRow.addView(secondColView, colNum);
        colColorFlag = false;
        for(int i = 2; i < rowNum; i++){
            int cellViewId = R.layout.checkbox_matte;
            if(colColorFlag)
                cellViewId = R.layout.checkbox_highlight;
            ViewGroup cellView = (ViewGroup) LayoutInflater.from(this).inflate(
                    cellViewId, tableRows.get(i - 2), false);
            tableRows.get(i - 2).addView(cellView, colNum);
            colColorFlag = !colColorFlag;
        }
        colNum++;
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
}
