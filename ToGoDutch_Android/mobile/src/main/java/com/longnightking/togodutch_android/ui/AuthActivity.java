package com.longnightking.togodutch_android.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.longnightking.togodutch_android.R;
import com.longnightking.togodutch_android.interfaces.AuthFragmentInteractListener;

public class AuthActivity extends Activity implements AuthFragmentInteractListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        if(savedInstanceState == null){
            loadFragment(true, false);
        }
    }

    private void loadFragment(boolean isSignIn, boolean isAddToBackStack){

        AbstractAuthFragment fragment = isSignIn ? new LoginFragment() : new SignUpFragment();
        fragment.registerFragmentInteractListener(this);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out);
        fragmentTransaction.replace(R.id.authFragmentContainer, fragment);
        if(isAddToBackStack){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteract(boolean isSignIn) {
        loadFragment(isSignIn, !isSignIn);
    }
}
