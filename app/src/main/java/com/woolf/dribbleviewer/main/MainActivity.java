package com.woolf.dribbleviewer.main;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.woolf.dribbleviewer.R;
import com.woolf.dribbleviewer.base.BaseActivity;
import com.woolf.dribbleviewer.data.ShotData;
import com.woolf.dribbleviewer.rest.listeners.IRestObserver;
import com.woolf.dribbleviewer.rest.listeners.Status;
import com.woolf.dribbleviewer.rest.managers.ErrorHandler;
import com.woolf.dribbleviewer.rest.models.ShotsModel;
import com.woolf.dribbleviewer.rest.params.RequestParams;
import com.woolf.dribbleviewer.rest.results.Pair;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by WoOLf on 23.02.2016.
 */
public class MainActivity extends BaseActivity implements IRestObserver {

    private static final String SHOT_LIST = "MainActivity.SHOT_LIST";


    @Bind(R.id.tb)
    Toolbar toolbar;
    @Bind(R.id.rv_main)
    RecyclerView rvShots;
    @Bind(R.id.srl_main)
    SwipeRefreshLayout srlReload;

    private ShotsModel mShotsModel;
    private int mShotsRequestId;

    private ArrayList<ShotData> mShotList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolbar();

        mShotsModel = getRequestFragment().getShotsModel();
        mShotsRequestId = mShotsModel.addObserver(this);
        if (savedInstanceState != null) {
            mShotList = savedInstanceState.getParcelableArrayList(SHOT_LIST);
        }

        if (mShotList == null || mShotList.isEmpty()) {
            mShotsModel.load(RequestParams.getShotsParams());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(SHOT_LIST, mShotList);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void reloadList() {

    }

    private void fillList() {

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onDestroy() {
        mShotsModel.deleteObserver(this);
        super.onDestroy();
    }

    @Override
    public void onStartLoading(int request_id) {
        if (mShotsRequestId == request_id) {

        }
    }

    @Override
    public void onCompleted(int request_id, Pair object) {
        if (mShotsRequestId == request_id) {

        }
    }

    @Override
    public void onError(int request_id, ErrorHandler error) {
        if (mShotsRequestId == request_id) {

        }
    }

    @Override
    public void onChangeStatus(int request_id, Status status) {
        if (mShotsRequestId == request_id) {

        }
    }
}
