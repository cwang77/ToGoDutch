package com.longnightking.togodutch_android.ui;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.longnightking.togodutch_android.R;

public class SignUpActivity extends Activity {

    private EditText username, password, passwordRepeat;

    private AutoCompleteTextView email;

    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        bindViews();
    }

    private void bindViews(){
        email = (AutoCompleteTextView)findViewById(R.id.email);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        passwordRepeat = (EditText)findViewById(R.id.password_repeat);
        registerBtn = (Button)findViewById(R.id.register_button);
        registerBtn.setEnabled(false);

        email.setOnFocusChangeListener(mFocusChangeListener);
        username.setOnFocusChangeListener(mFocusChangeListener);
        password.setOnFocusChangeListener(mFocusChangeListener);
        passwordRepeat.setOnFocusChangeListener(mFocusChangeListener);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerBtn.setEnabled(false);
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
