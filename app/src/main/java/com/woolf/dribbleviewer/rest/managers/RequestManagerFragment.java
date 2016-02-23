package com.woolf.dribbleviewer.rest.managers;

import android.app.Fragment;
import android.os.Bundle;

import com.woolf.dribbleviewer.rest.models.ShotsModel;

/**
 * Created by woolf on 12.02.16.
 */
public class RequestManagerFragment extends Fragment {

    public static final String TAG = "RequestManagerFragment.TAG";


    private ShotsModel mShotsModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /**
     * Shots
     */
    public ShotsModel getShotsModel() {
        if (mShotsModel == null) {
            mShotsModel = new ShotsModel();
        }
        return mShotsModel;
    }

    public void destroySignInModel() {
        mShotsModel = null;
    }


}
