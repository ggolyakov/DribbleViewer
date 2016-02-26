package com.woolf.dribbleviewer.rest.managers;

import android.app.Fragment;
import android.os.Bundle;

import com.woolf.dribbleviewer.rest.models.DataBaseModel;
import com.woolf.dribbleviewer.rest.models.ShotsModel;
import com.woolf.dribbleviewer.rest.params.Constants;

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
            mShotsModel = new ShotsModel(Constants.SHOTS_REQUEST_ID);
        }
        return mShotsModel;
    }

    /**
     * DataBase
     */
    public DataBaseModel getDBModel() {
        if (mDBModel == null) {
            mDBModel = new DataBaseModel(Constants.DB_REQUEST_ID);
        }
        return mDBModel;
    }


}
