package com.longnightking.togodutch_android.ui;

import android.app.Fragment;

/**
 * Created by changye on 9/25/15.
 */
public class UIController {
    private static UIController instance;

    UserStatus currUserStatus;
    private UIController(){
        currUserStatus = UserStatus.UNAUTH;
    }

    public UIController getInstance(){
        if(instance == null){
            instance = new UIController();
        }
        return instance;
    }

    private enum UserStatus {
        UNAUTH, LOGGEDIN
    }

    public void login(){
        currUserStatus = UserStatus.LOGGEDIN;
    }

    public void logout(){
        currUserStatus = UserStatus.UNAUTH;
    }

    public Fragment getCurrentFragment(){
        return null;
    }
}
