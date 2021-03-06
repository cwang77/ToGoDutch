package com.longnightking.togodutch_android.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.longnightking.togodutch_android.interfaces.OnScrollListener;

/**
 * Created by longNightKing on 4/18/15.
 */
public class ObservableScrollView extends ScrollView {

    private OnScrollListener mOnScrollListener;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(OnScrollListener scrollViewListener) {
        this.mOnScrollListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);
        if(mOnScrollListener != null) {
            mOnScrollListener.onScrollStateChanged(this, x, y, oldX, oldY);
        }
    }
}
