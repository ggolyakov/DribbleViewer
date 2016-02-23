package com.woolf.dribbleviewer.base;

import android.support.v7.app.AppCompatActivity;

import com.woolf.dribbleviewer.rest.managers.RequestManagerFragment;


public class BaseActivity extends AppCompatActivity {

    private RequestManagerFragment mStorageFragment;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            mStorageFragment = null;
        }
    }

    protected RequestManagerFragment getRequestFragment() {
        if (mStorageFragment != null) {
            return mStorageFragment;
        } else {
            if (getFragmentManager().findFragmentByTag(RequestManagerFragment.TAG) != null) {
                mStorageFragment = (RequestManagerFragment) getFragmentManager().findFragmentByTag(RequestManagerFragment.TAG);
                return mStorageFragment;
            } else {
                mStorageFragment = new RequestManagerFragment();
                getFragmentManager().beginTransaction().add(mStorageFragment, RequestManagerFragment.TAG).commit();
                return mStorageFragment;
            }
        }
    }
}
