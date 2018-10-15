package com.example.test.dharmrajmachinetest.ui.base;

import android.content.Context;
import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    protected Listener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

        if(context instanceof Listener)
            listener = (Listener) context;

    }


    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        mContext = null;
    }
}
