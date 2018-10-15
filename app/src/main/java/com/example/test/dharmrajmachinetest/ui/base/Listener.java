package com.example.test.dharmrajmachinetest.ui.base;

import android.support.v4.app.Fragment;

public interface Listener {

    void showLoader();
    void hideLoader();

    void addFragment(Fragment fragment, boolean adTtoBackStack);
    void replaceFragment(Fragment fragment, boolean adTtoBackStack);

    void showToast(String msg);
}
