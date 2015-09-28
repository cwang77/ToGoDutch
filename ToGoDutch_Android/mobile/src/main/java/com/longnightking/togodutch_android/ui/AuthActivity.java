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
            loadFragment(-1, false);
        }
    }

    private void loadFragment(int viewId, boolean isAddToBackStack){
        AbstractAuthFragment fragment = null;
        switch (viewId){
            case R.id.signUpTxt:
                fragment = new SignUpFragment();
                break;
            case R.id.forgotPasswordTxt:
                fragment = new PasswordForgotFragment();
                break;
            default:
                fragment = new LoginFragment();
                break;
        }
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
    public void onFragmentInteract(int viewId) {
        loadFragment(viewId, true);
    }
}
