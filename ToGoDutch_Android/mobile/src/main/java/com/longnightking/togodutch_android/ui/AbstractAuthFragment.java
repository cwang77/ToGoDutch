package com.longnightking.togodutch_android.ui;

import android.app.Fragment;

import com.longnightking.togodutch_android.interfaces.AuthFragmentInteractListener;

/**
 * Created by changye on 9/25/15.
 */
public class AbstractAuthFragment extends Fragment {

    public AbstractAuthFragment(){
    }

    protected AuthFragmentInteractListener fragmentInteractListener;

    public void registerFragmentInteractListener(AuthFragmentInteractListener l){
        fragmentInteractListener = l;
    }
}
