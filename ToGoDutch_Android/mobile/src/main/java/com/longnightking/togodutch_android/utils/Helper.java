package com.longnightking.togodutch_android.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.longnightking.togodutch_android.R;
import com.longnightking.togodutch_android.interfaces.OnDialogSelectionListener;
import com.parse.ParseException;

/**
 * Created by changye on 9/18/15.
 */
public class Helper {
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

    public static void handleFailure(ParseException e){

    }
}
