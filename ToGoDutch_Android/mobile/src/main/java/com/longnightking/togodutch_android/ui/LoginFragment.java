package com.longnightking.togodutch_android.ui;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.longnightking.togodutch_android.R;
import com.longnightking.togodutch_android.utils.Helper;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends AbstractAuthFragment {

    private TextView forgotPswdTxt, signUpTxt;

    private Button signInBtn;

    private AutoCompleteTextView emailTxt;

    private EditText passwordTxt;

    private ProgressBar loginWaiting;

    public static final String TAG = LoginFragment.class.getName();

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId(){
        return R.layout.fragment_login;
    }

    @Override
    protected void bindViews(View rootView){
        forgotPswdTxt = (TextView)rootView.findViewById(R.id.forgotPasswordTxt);
        signUpTxt = (TextView)rootView.findViewById(R.id.signUpTxt);
        emailTxt = (AutoCompleteTextView)rootView.findViewById(R.id.email);
        passwordTxt = (EditText)rootView.findViewById(R.id.password);
        signInBtn = (Button)rootView.findViewById(R.id.button);
        loginWaiting = (ProgressBar)rootView.findViewById(R.id.waitingProgressBar);
        signInBtn.setText(getString(R.string.action_sign_in));
        appendUnderlineForTxtView(forgotPswdTxt);
        appendUnderlineForTxtView(signUpTxt);

        emailTxt.setOnFocusChangeListener(mOnFocusChangeListener);
        passwordTxt.setOnFocusChangeListener(mOnFocusChangeListener);

        forgotPswdTxt.setOnClickListener(redirectClickListener);
        signUpTxt.setOnClickListener(redirectClickListener);
    }

    private void appendUnderlineForTxtView(TextView txtView){
        txtView.setPaintFlags(txtView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    private View.OnClickListener redirectClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(fragmentInteractListener != null){
                fragmentInteractListener.onFragmentInteract(v.getId());
            }else{
                ((AuthActivity)getActivity()).onFragmentInteract(v.getId());
            }
        }
    };

    @Override
    protected boolean checkInputValidation(){
        boolean isEmailValid = Helper.isEmailValid(emailTxt.getText().toString());
        boolean isPasswordNoEmpty = !passwordTxt.getText().toString().isEmpty();
        if(!isEmailValid){
            emailTxt.setError(getString(R.string.email_invalid));
        }
        if(!isPasswordNoEmpty){
            passwordTxt.setError(getString(R.string.password_empty));
        }
        return isEmailValid && isPasswordNoEmpty;
    }

    @Override
    protected void buttonOperation(){
        ParseUser.logInInBackground(emailTxt.getText().toString(), passwordTxt.getText().toString(), new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Helper.log(TAG, "user login successfully");
                    Helper.redirectTo(getActivity(), new Intent(getActivity(), MainActivity.class));
                } else {
                    Helper.log(TAG, "user login failed");
                    reactiveInputUI();
                    Helper.handleFailureForView(e, getActivity(), emailTxt, passwordTxt);
                }
            }
        });
    }

    @Override
    protected List<EditText> getInputViews(){
        List<EditText> rtnList = new ArrayList<EditText>();
        rtnList.add(emailTxt);
        rtnList.add(passwordTxt);
        return rtnList;
    }

    @Override
    protected View getWaitingProgressBar(){
        return loginWaiting;
    }

    @Override
    protected Button getButton() {
        return signInBtn;
    }
}
