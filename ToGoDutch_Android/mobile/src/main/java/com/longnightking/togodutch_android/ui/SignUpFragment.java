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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        bindViews(view);
        return view;
    }

    private void bindViews(View rootView){
        email = (AutoCompleteTextView)rootView.findViewById(R.id.email);
        password = (EditText)rootView.findViewById(R.id.password);
        passwordRepeat = (EditText)rootView.findViewById(R.id.password_repeat);
        registerBtn = (Button)rootView.findViewById(R.id.register_button);
        signUpWaiting = (ProgressBar)rootView.findViewById(R.id.signUpWaiting);

        email.setOnFocusChangeListener(mFocusChangeListener);
        password.setOnFocusChangeListener(mFocusChangeListener);
        passwordRepeat.setOnFocusChangeListener(mFocusChangeListener);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableInputUI();
                checkInputValidation();
                signUpNewUser();
            }
        });
    }

    private View.OnFocusChangeListener mFocusChangeListener = new View.OnFocusChangeListener() {

        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                EditText thisEditTxt = (EditText)v;
                if(thisEditTxt.getText().length() <= 0)
                    ;
            }else{

            }

        }
    };

    private boolean checkInputValidation(){
        return false;
    }

    private void signUpNewUser(){
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
                    Helper.handleFailure(e);
                }
            }
        });
    }

    private void disableInputUI(){
        registerBtn.setEnabled(false);
        email.setEnabled(false);
        password.setEnabled(false);
        passwordRepeat.setEnabled(false);
        signUpWaiting.setVisibility(View.VISIBLE);
    }

    private void reactiveInputUI(){
        registerBtn.setEnabled(true);
        email.setEnabled(true);
        password.setEnabled(true);
        passwordRepeat.setEnabled(true);
        signUpWaiting.setVisibility(View.GONE);
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
