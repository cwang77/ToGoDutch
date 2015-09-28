package com.longnightking.togodutch_android.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.longnightking.togodutch_android.R;
import com.longnightking.togodutch_android.interfaces.AuthFragmentInteractListener;

import java.util.List;

/**
 * Created by changye on 9/25/15.
 */
public abstract class AbstractAuthFragment extends Fragment {

    private Button operationBtn;
    protected AuthFragmentInteractListener fragmentInteractListener;

    public AbstractAuthFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        bindViews(view);
        operationBtn = getButton();
        if(operationBtn != null){
            operationBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    disableInputUI();
                    if (checkInputValidation()) {
                        buttonOperation();
                    } else {
                        reactiveInputUI();
                    }
                }
            });
        }
        return view;
    }

    public void registerFragmentInteractListener(AuthFragmentInteractListener l){
        fragmentInteractListener = l;
    }

    protected void disableInputUI(){
        for(View view : getInputViews()){
            view.setEnabled(false);
        }
        getButton().setEnabled(false);
        getWaitingProgressBar().setVisibility(View.VISIBLE);
    }

    protected void reactiveInputUI(){
        for(EditText view : getInputViews()){
            view.setEnabled(true);
        }
        getButton().setEnabled(true);
        getWaitingProgressBar().setVisibility(View.GONE);
    }

    protected View.OnFocusChangeListener mOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus){
                EditText view = getInputViewById(v.getId());
                if(view != null){
                    view.setError(null);
                }
            }
        }
    };

    private EditText getInputViewById(int viewId){
        for(EditText view : getInputViews()){
            if(view.getId() == viewId){
                return view;
            }
        }
        return null;
    }

    protected abstract int getLayoutId();
    protected abstract List<EditText> getInputViews();
    protected abstract View getWaitingProgressBar();
    protected abstract Button getButton();
    protected abstract void bindViews(View view);
    protected abstract boolean checkInputValidation();
    protected abstract void buttonOperation();
}
