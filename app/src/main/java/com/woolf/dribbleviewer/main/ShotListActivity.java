package com.woolf.dribbleviewer.main;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.woolf.dribbleviewer.DribbleApplication;
import com.woolf.dribbleviewer.R;
import com.woolf.dribbleviewer.base.BaseActivity;
import com.woolf.dribbleviewer.models.ShotData;
import com.woolf.dribbleviewer.rest.listeners.IRestObserver;
import com.woolf.dribbleviewer.rest.listeners.Status;
import com.woolf.dribbleviewer.rest.managers.ErrorHandler;
import com.woolf.dribbleviewer.rest.models.ShotsModel;
import com.woolf.dribbleviewer.rest.params.RequestParams;
import com.woolf.dribbleviewer.rest.results.Pair;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShotListActivity extends BaseActivity implements IRestObserver, SwipeRefreshLayout.OnRefreshListener {

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

    private ShotsListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



        initToolbar();
        initAdapters();
        setListeners();

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

    private void initAdapters() {
        mListAdapter = new ShotsListAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(DribbleApplication.APP_CONTEXT);

        rvShots.setAdapter(mListAdapter);
        rvShots.setLayoutManager(manager);
    }

    private void reloadList() {

    }

    private void fillList() {

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void setListeners(){
        srlReload.setOnRefreshListener(this);

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
            ArrayList<ShotData> data = (ArrayList<ShotData>) object.getValue();
            mListAdapter.setData(data);
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

    @Override
    public void onRefresh() {
        rvShots.setLayoutManager(new GridLayoutManager(DribbleApplication.APP_CONTEXT,2));
    }
}
