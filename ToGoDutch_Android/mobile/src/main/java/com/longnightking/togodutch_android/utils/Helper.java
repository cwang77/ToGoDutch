package com.longnightking.togodutch_android.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.longnightking.togodutch_android.R;
import com.longnightking.togodutch_android.interfaces.OnDialogSelectionListener;

/**
 * Created by changye on 9/18/15.
 */
public class Helper {
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
}
