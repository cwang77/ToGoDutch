package com.longnightking.togodutch_android.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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

import com.longnightking.togodutch_android.R;
import com.longnightking.togodutch_android.interfaces.OnScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longNightKing on 4/21/15.
 */
public class TableView extends RelativeLayout {

    private Context mContext;

    private TableInitResIds resIds;

    private int rowNum = 1, colNum = 1;

    private ViewGroup checkBoxesContainer, horizontalHeader, verticalHeader, topLeftCornerViewContainer;

    private ObservableHorizontalScrollView mHorizontalScrollView, horizontalHeaderContainer;

    private ObservableScrollView mVerticalScrollView, verticalHeaderContainer;

    private List<ViewGroup> tableRows;

    private Boolean colorFlag = false;

    public TableView(Context context, TableInitResIds Ids, int rNum, int cNum){
        super(context);
        init(context, Ids, rNum, cNum);
    }

    public TableView(Context context, AttributeSet attrs, TableInitResIds Ids, int rNum, int cNum){
        super(context, attrs);
        init(context, Ids, rNum, cNum);
    }
    public TableView(Context context, AttributeSet attrs, int defStyleAttr, TableInitResIds Ids, int rNum, int cNum){
        super(context, attrs, defStyleAttr);
        init(context, Ids, rNum, cNum);
    }

    private void init(Context context, TableInitResIds Ids, int r, int c){
        mContext = context;
        resIds = Ids;
        inflate(mContext, R.layout.widget_tableview, this);
        bindViews(r, c);
    }

    private void bindViews(int r, int c){
        checkBoxesContainer = (ViewGroup)findViewById(R.id.checkBoxesContainer);
        horizontalHeader = (ViewGroup)findViewById(R.id.horizontalHeader);
        verticalHeader = (ViewGroup)findViewById(R.id.verticalHeader);
        mHorizontalScrollView = (ObservableHorizontalScrollView)findViewById(R.id.horizontalScrollBar);
        mVerticalScrollView = (ObservableScrollView)findViewById(R.id.verticalScrollBar);
        verticalHeaderContainer = (ObservableScrollView)findViewById(R.id.verticalHeaderContainer);
        horizontalHeaderContainer = (ObservableHorizontalScrollView)findViewById(R.id.horizontalHeaderContainer);
        topLeftCornerViewContainer = (FrameLayout)findViewById(R.id.topLeftCornerContainer);
        tableRows = new ArrayList<ViewGroup>();

        topLeftCornerViewContainer.addView(LayoutInflater.from(mContext).inflate(
                resIds.mTopLeftCornerViewResId, topLeftCornerViewContainer, false));
        verticalHeader.addView(LayoutInflater.from(mContext).inflate(
                resIds.mVHeaderUnitResId_H, verticalHeader, false));
        horizontalHeader.addView(LayoutInflater.from(mContext).inflate(
                resIds.mHHeaderUnitResId, horizontalHeader, false));
        TableRow firstRow = new TableRow(mContext);
        ViewGroup checkBoxInFirstRow = (ViewGroup) LayoutInflater.from(mContext).inflate(
                resIds.mTableCellResId_H, firstRow, false);
        firstRow.addView(checkBoxInFirstRow);
        checkBoxesContainer.addView(firstRow);
        tableRows = new ArrayList<ViewGroup>();
        bindCheckBox(checkBoxInFirstRow, 0);

        mHorizontalScrollView.setScrollViewListener(mOnScrollListener);
        mVerticalScrollView.setScrollViewListener(mOnScrollListener);
        verticalHeaderContainer.setOnTouchListener(mTableHeaderOnTouchDoNothingListener);
        horizontalHeaderContainer.setOnTouchListener(mTableHeaderOnTouchDoNothingListener);
        tableRows.add(firstRow);

        initTableViews(r, c);
    }

    private void initTableViews(int rN, int cN){
        for(int i = 1; i < rN; i++)
            addRow();
        for(int i = 1; i < cN; i++)
            addColumn();
    }

    public void addRow(){
        int headViewId = resIds.mVHeaderUnitResId_M;
        int cellViewId = resIds.mTableCellResId_M;
        if(colorFlag) {
            cellViewId = resIds.mTableCellResId_H;
            headViewId = resIds.mVHeaderUnitResId_H;
        }
        View headerView = LayoutInflater.from(mContext).inflate(
                headViewId, verticalHeader, false);
        TableRow rowView = new TableRow(mContext);
        for(int i = 0; i < colNum; i++)
        {
            ViewGroup cellView = (ViewGroup) LayoutInflater.from(mContext).inflate(
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

    public void addColumn(){
        ViewGroup headerView = (ViewGroup) LayoutInflater.from(mContext).inflate(
                resIds.mHHeaderUnitResId, horizontalHeader, false);
        horizontalHeader.addView(headerView, colNum);
        colorFlag = true;
        for(int i = 0; i < rowNum; i++){
            int cellViewId = resIds.mTableCellResId_M;
            if(colorFlag)
                cellViewId = resIds.mTableCellResId_H;
            ViewGroup cellView = (ViewGroup) LayoutInflater.from(mContext).inflate(
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
                Toast.makeText(mContext, "Checked: " + checkedTxt + ", Row: " + mRow + ", Col: " + mCol, Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(mContext, "checkbox not exist, mId: " + mId, Toast.LENGTH_SHORT).show();
        }
    };

    private OnScrollListener mOnScrollListener = new OnScrollListener(){
        @Override
        public void onScrollStateChanged(FrameLayout mScrollView, int x, int y, int oldX, int oldY){
            if(mScrollView.getId() == R.id.horizontalScrollBar){
                horizontalHeaderContainer.scrollTo(x, y);
            }else if(mScrollView.getId() == R.id.verticalScrollBar){
                verticalHeaderContainer.scrollTo(x, y);
            }
        }
    };

    private View.OnTouchListener mTableHeaderOnTouchDoNothingListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    };

    public static class TableInitResIds{
        public TableInitResIds(){
            mHHeaderUnitResId = R.layout.widget_table_column_header_unit;
            mVHeaderUnitResId_H = R.layout.widget_table_row_header_unit_highlight;
            mVHeaderUnitResId_M = R.layout.widget_table_row_header_unit_matte;
            mTopLeftCornerViewResId = R.layout.widget_table_topleft_corner_view;
            mTableCellResId_H = R.layout.widget_table_cell_unit_highlight;
            mTableCellResId_M = R.layout.widget_table_cell_unit_matte;
        }
        public int mHHeaderUnitResId, mVHeaderUnitResId_H, mVHeaderUnitResId_M, mTopLeftCornerViewResId, mTableCellResId_H, mTableCellResId_M;
    }
}
