package com.longnightking.togodutch_android.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.longnightking.togodutch_android.R;
import com.longnightking.togodutch_android.interfaces.OnDialogSelectionListener;
import com.parse.ParseException;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * Created by changye on 9/18/15.
 */
public class Helper {
    private static final String TAG = "Helper";
    private static final boolean DEBUG = true;
    public static void showOkOrCancelPopupDialog(Context c, String messageToShow, String title, final OnDialogSelectionListener l) {
        boolean rtn = false;
        new AlertDialog.Builder(c)
                .setTitle(title)
                .setMessage(messageToShow)
                .setPositiveButton(c.getString(R.string.dialog_OK),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            l.onPositiveSelected();
                        }
                    })
                .setNegativeButton(
                        c.getString(R.string.dialog_Cancel),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            l.onNegativeSelected();
                        }
                    }).create().show();
    }

    public static void log(String TAG, String message){
        if(DEBUG){
            Log.i(TAG, message);
        }
    }

    public static void redirectTo(Activity currActivity, Intent intent){
        currActivity.startActivity(intent);
        currActivity.finish();
    }

    public static void handleFailure(ParseException e, Context context){
        handleFailureForView(e, context, null, null);
    }

    public static void handleFailureForView(ParseException e, Context context, EditText email, EditText password){
        log(TAG, "Error code: " + e.getCode() + ", exception detail: " + e.getMessage());
        String message = null;
        switch(e.getCode()){
            case ParseException.USERNAME_TAKEN:
                if(email != null){
                    email.setError(e.getMessage());
                }
                message = e.getMessage();
                break;
            case ParseException.CONNECTION_FAILED:
                message = "Connection failed!";
                break;
            default: break;
        }
        if(message == null){
            message = e.getMessage();
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isEmailValid(String emailAddress){
        EmailValidator validator = EmailValidator.getInstance(false);
        return validator.isValid(emailAddress);
    }
}
