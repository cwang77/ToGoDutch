package com.longnightking.togodutch_android.global;

import android.content.Context;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longNightKing on 5/4/15.
 */
public class UserManagement {

    private static UserManagement instance = null;

    private Context mContext;


    private List<String> permissions;

    private UserManagement(Context c){
        mContext = c;
        permissions = new ArrayList<>();
        permissions.add("public_profile");
        permissions.add("email");
        permissions.add("user_friends");
    };

    public static synchronized UserManagement getInstance(Context c){
        if(instance == null)
            instance = new UserManagement(c);
        return instance;
    }

    public List<String> getPermissionsList(){
        return permissions;
    }

}
