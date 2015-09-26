package com.longnightking.togodutch_android.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.longnightking.togodutch_android.R;
import com.longnightking.togodutch_android.interfaces.AuthFragmentInteractListener;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        bindViews(view);
        return view;
    }

    private void bindViews(View rootView){
        forgotPswdTxt = (TextView)rootView.findViewById(R.id.forgotPasswordTxt);
        signUpTxt = (TextView)rootView.findViewById(R.id.signUpTxt);
        emailTxt = (AutoCompleteTextView)rootView.findViewById(R.id.email);
        passwordTxt = (EditText)rootView.findViewById(R.id.password);
        signInBtn = (Button)rootView.findViewById(R.id.email_sign_in_button);
        loginWaiting = (ProgressBar)rootView.findViewById(R.id.loginWaiting);

        appendUnderlineForTxtView(forgotPswdTxt);
        appendUnderlineForTxtView(signUpTxt);

        forgotPswdTxt.setOnClickListener(mViewClickListener);
        signUpTxt.setOnClickListener(mViewClickListener);
        signInBtn.setOnClickListener(mViewClickListener);
    }

    private void appendUnderlineForTxtView(TextView txtView){
        txtView.setPaintFlags(txtView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    private View.OnClickListener mViewClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.forgotPasswordTxt:
                    break;
                case R.id.signUpTxt:
                    fragmentInteractListener.onFragmentInteract(false);
                    break;
                case R.id.email_sign_in_button:
                    disableInputUI();
                    signUserIn();
                    break;
                default: break;
            }
        }
    };

    private void signUserIn(){
        ParseUser.logInInBackground(emailTxt.getText().toString(), passwordTxt.getText().toString(), new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // Hooray! The user is logged in.
                } else {
                    reactiveInputUI();
                    // Signup failed. Look at the ParseException to see what happened.
                }
            }
        });
    }

    private void disableInputUI(){
        signInBtn.setEnabled(false);
        emailTxt.setEnabled(false);
        passwordTxt.setEnabled(false);
        loginWaiting.setVisibility(View.VISIBLE);
    }

    private void reactiveInputUI(){
        signInBtn.setEnabled(true);
        emailTxt.setEnabled(true);
        passwordTxt.setEnabled(true);
        loginWaiting.setVisibility(View.GONE);
    }
}
