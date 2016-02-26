package com.woolf.dribbleviewer.base;

import android.support.v7.app.AppCompatActivity;

import com.woolf.dribbleviewer.rest.managers.RequestManagerFragment;


public class BaseActivity extends AppCompatActivity {

    private RequestManagerFragment mRequestFragment;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            mRequestFragment = null;
        }
    }


    protected RequestManagerFragment getRequestFragment() {
        if (mRequestFragment != null) {
            return mRequestFragment;
        } else {
            if (getFragmentManager().findFragmentByTag(RequestManagerFragment.TAG) != null) {
                mRequestFragment = (RequestManagerFragment) getFragmentManager().findFragmentByTag(RequestManagerFragment.TAG);
                return mRequestFragment;
            } else {
                mRequestFragment = new RequestManagerFragment();
                getFragmentManager().beginTransaction().add(mRequestFragment, RequestManagerFragment.TAG).commit();
                return mRequestFragment;
            }
        }
    }
}
