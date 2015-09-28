package com.longnightking.togodutch_android.ui;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.longnightking.togodutch_android.R;
import com.longnightking.togodutch_android.interfaces.AuthFragmentInteractListener;
import com.longnightking.togodutch_android.utils.Helper;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends AbstractAuthFragment {

    private EditText password, passwordRepeat;

    private AutoCompleteTextView email;

    private Button registerBtn;

    private ProgressBar signUpWaiting;

    private final static String TAG = SignUpFragment.class.getSimpleName();

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId(){
        return R.layout.fragment_sign_up;
    }

    @Override
    protected void bindViews(View rootView){
        email = (AutoCompleteTextView)rootView.findViewById(R.id.email);
        password = (EditText)rootView.findViewById(R.id.password);
        passwordRepeat = (EditText)rootView.findViewById(R.id.password_repeat);
        registerBtn = (Button)rootView.findViewById(R.id.button);
        signUpWaiting = (ProgressBar)rootView.findViewById(R.id.waitingProgressBar);
        registerBtn.setText(R.string.register_btn_txt);
//        email.addTextChangedListener(emailTextWatcher);
//        passwordRepeat.addTextChangedListener(passwordRepeatWatcher);
        email.setOnFocusChangeListener(mOnFocusChangeListener);
        password.setOnFocusChangeListener(mOnFocusChangeListener);
        passwordRepeat.setOnFocusChangeListener(mOnFocusChangeListener);
    }

    @Override
    protected boolean checkInputValidation(){
        boolean isEmailValid = Helper.isEmailValid(email.getText().toString());
        String passwordTxt = password.getText().toString();
        String passwordRepeatTxt = passwordRepeat.getText().toString();
        boolean isPasswordMatch = !passwordTxt.isEmpty() && !passwordRepeatTxt.isEmpty() && passwordRepeatTxt.equals(passwordTxt);
        if(!isEmailValid){
            email.setError(getString(R.string.email_invalid));
        }
        if(!isPasswordMatch){
            passwordRepeat.setError(getString(R.string.password_not_match));
        }
        return isEmailValid && isPasswordMatch;
    }

    @Override
    protected void buttonOperation(){
        ParseUser user = new ParseUser();
        user.setUsername(email.getText().toString());
        user.setPassword(passwordRepeat.getText().toString());
        user.setEmail(email.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Helper.log(TAG, "sign up successfully");
                    Helper.redirectTo(getActivity(), new Intent(getActivity(), MainActivity.class));
                } else {
                    Helper.log(TAG, "sign up failed");
                    reactiveInputUI();
                    Helper.handleFailureForView(e, getActivity(), email, passwordRepeat);
                }
            }
        });
    }

    @Override
    protected List<EditText> getInputViews(){
        List<EditText> rtnList = new ArrayList<EditText>();
        rtnList.add(email);
        rtnList.add(password);
        rtnList.add(passwordRepeat);
        return rtnList;
    }

    @Override
    protected View getWaitingProgressBar(){
        return signUpWaiting;
    }

    @Override
    protected Button getButton() {
        return registerBtn;
    }

/*    private TextWatcher emailTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            if(Helper.isEmailValid(s.toString())){
                email.setError(null);
            }else{
                email.setError(getString(R.string.email_invalid));
            }
        }
    };

    private TextWatcher passwordRepeatWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            if(s.equals(password.getText())){
                passwordRepeat.setError(null);
            }else{
                passwordRepeat.setError(getString(R.string.password_not_match));
            }
        }
    }; */
}
