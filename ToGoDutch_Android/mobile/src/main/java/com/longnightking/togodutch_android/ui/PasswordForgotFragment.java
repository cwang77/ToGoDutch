package com.longnightking.togodutch_android.ui;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.longnightking.togodutch_android.R;
import com.longnightking.togodutch_android.utils.Helper;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PasswordForgotFragment extends AbstractAuthFragment {

    private Button resetPasswordBtn;
    private EditText email;
    private ProgressBar resetWaiting;

    private static final String TAG = PasswordForgotFragment.class.getSimpleName();

    public PasswordForgotFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId(){
        return R.layout.fragment_password_forgot;
    }

    @Override
    protected void bindViews(View rootView){
        resetPasswordBtn = (Button) rootView.findViewById(R.id.button);
        resetWaiting = (ProgressBar)rootView.findViewById(R.id.waitingProgressBar);
        email = (EditText)rootView.findViewById(R.id.email);
        resetPasswordBtn.setText(R.string.reset_btn_txt);
    }

    @Override
    protected List<EditText> getInputViews(){
        List<EditText> rtnList = new ArrayList<EditText>();
        rtnList.add(email);
        return rtnList;
    }

    @Override
    protected View getWaitingProgressBar(){
        return resetWaiting;
    }

    @Override
    protected Button getButton(){
        return resetPasswordBtn;
    }
    @Override
    protected boolean checkInputValidation(){
        return Helper.isEmailValid(email.getText().toString());
    }
    @Override
    protected void buttonOperation(){
        ParseUser.requestPasswordResetInBackground(email.getText().toString(), new RequestPasswordResetCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Helper.log(TAG, "successfully sent password reset email");
                    getActivity().onBackPressed();
                } else {
                    Helper.log(TAG, "password reset failed");
                    reactiveInputUI();
                    Helper.handleFailureForView(e, getActivity(), email, null);
                }
            }
        });
    }
}
