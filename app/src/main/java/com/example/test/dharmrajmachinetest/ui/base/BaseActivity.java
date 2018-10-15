package com.example.test.dharmrajmachinetest.ui.base;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity implements Listener{


    @Override
    public void addFragment(Fragment fragment, boolean adTtoBackStack) {
        addReplacefragment(true, fragment, adTtoBackStack);
    }

    @Override
    public void replaceFragment(Fragment fragment, boolean adTtoBackStack) {
        addReplacefragment(false, fragment, adTtoBackStack);
    }

    private void addReplacefragment(boolean addFragment, Fragment fragment, boolean addToBackStack){

        String backStackName = fragment.getClass().getName();
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);

        if (!fragmentPopped) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            //transaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_in,0,0);

            if(addFragment)
                transaction.add(getContainerViewId(), fragment, backStackName);
            else
                transaction.replace(getContainerViewId(), fragment, backStackName);

            if (addToBackStack)
                transaction.addToBackStack(backStackName);

            transaction.commit();
        }
    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void showLoader() {

    }

    @Override
    public void showToast(String msg) {
        if(!TextUtils.isEmpty(msg))
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    public abstract @IdRes int getContainerViewId();
}
