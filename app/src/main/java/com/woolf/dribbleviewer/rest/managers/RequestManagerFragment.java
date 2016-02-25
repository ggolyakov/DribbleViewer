package com.woolf.dribbleviewer.rest.managers;

import android.app.Fragment;
import android.os.Bundle;

import com.woolf.dribbleviewer.rest.models.DataBaseModel;
import com.woolf.dribbleviewer.rest.models.ShotsModel;

public class RequestManagerFragment extends Fragment {

    public static final String TAG = "RequestManagerFragment.TAG";


    private ShotsModel mShotsModel;
    private DataBaseModel mDBModel;

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

    /**
     * DataBase
     */
    public DataBaseModel getDBModel() {
        if (mDBModel == null) {
            mDBModel = new DataBaseModel();
        }
        return mDBModel;
    }

    public void destroyDBInModel() {
        mDBModel = null;
    }


}
