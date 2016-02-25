package com.woolf.dribbleviewer.main;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.woolf.dribbleviewer.DribbleApplication;
import com.woolf.dribbleviewer.R;
import com.woolf.dribbleviewer.base.BaseActivity;
import com.woolf.dribbleviewer.models.ShotData;
import com.woolf.dribbleviewer.rest.listeners.IRestObserver;
import com.woolf.dribbleviewer.rest.listeners.Status;
import com.woolf.dribbleviewer.rest.managers.ErrorHandler;
import com.woolf.dribbleviewer.rest.models.DataBaseModel;
import com.woolf.dribbleviewer.rest.models.ShotsModel;
import com.woolf.dribbleviewer.rest.params.RequestParams;
import com.woolf.dribbleviewer.rest.results.Pair;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShotListActivity extends BaseActivity implements IRestObserver, SwipeRefreshLayout.OnRefreshListener {

    private static final String SHOT_LIST = "MainActivity.SHOT_LIST";
    private static final String SHOT_REQUEST_ID = "MainActivity.SHOT_REQUEST_ID";
    private static final String DB_REQUEST_ID = "MainActivity.DB_REQUEST_ID";


    @Bind(R.id.rv_main)
    RecyclerView rvShots;
    @Bind(R.id.srl_main)
    SwipeRefreshLayout srlReload;

    private ShotsModel mShotsModel;
    private int mShotsRequestId;

    private DataBaseModel mDbModel;
    private int mDbRequestId;

    private ArrayList<ShotData> mShotList;

    private ShotsListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_list);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            mShotList = savedInstanceState.getParcelableArrayList(SHOT_LIST);
            mDbRequestId = savedInstanceState.getInt(DB_REQUEST_ID);
            mDbRequestId = savedInstanceState.getInt(DB_REQUEST_ID);
        }

        subscribe();
        initAdapters();
        setListeners();

        if (mShotList == null) {
            mDbModel.load();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(SHOT_LIST, mShotList);
        outState.putInt(SHOT_REQUEST_ID, mDbRequestId);
        outState.putInt(DB_REQUEST_ID, mDbRequestId);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mShotList = savedInstanceState.getParcelableArrayList(SHOT_LIST);
        mDbRequestId = savedInstanceState.getInt(DB_REQUEST_ID);
        mDbRequestId = savedInstanceState.getInt(DB_REQUEST_ID);
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


    private void subscribe() {
        mShotsModel = getRequestFragment().getShotsModel();
        mDbModel = getRequestFragment().getDBModel();

        mShotsRequestId = mShotsModel.addObserver(this);
        mDbRequestId = mDbModel.addObserver(this);
    }

    private void unsubscribe() {
        mShotsModel.deleteObserver(this);
        mDbModel.deleteObserver(this);
    }

    private void setListeners(){
        srlReload.setOnRefreshListener(this);

    }

    @Override
    protected void onDestroy() {
        unsubscribe();
        super.onDestroy();
    }

    @Override
    public void onStartLoading(int request_id) {
        if(mDbRequestId == request_id){
            Log.e("TAG","DB OnStart");
        }
        if (mShotsRequestId == request_id) {
            Log.e("TAG","SHOTS OnStart");
        }

    }

    @Override
    public void onCompleted(int request_id, Pair object) {
        if(mDbRequestId == request_id){
            Log.e("TAG","DB onCompleted");
            if(object.getValue() == null){
                mShotsModel.load(RequestParams.getShotsParams());
            }else {
                ArrayList<ShotData> data = (ArrayList<ShotData>) object.getValue();
                mListAdapter.setData(data);
            }
        }
        if (mShotsRequestId == request_id) {
            Log.e("TAG","SHOTS onCompleted");
            ArrayList<ShotData> data = (ArrayList<ShotData>) object.getValue();
            mListAdapter.setData(data);
        }
    }

    @Override
    public void onError(int request_id, ErrorHandler error) {

        if(mDbRequestId == request_id){
            Log.e("TAG", "DB onError");
        }
        if (mShotsRequestId == request_id) {
            Log.e("TAG", "SHOTS onError");
        }
    }

    @Override
    public void onChangeStatus(int request_id, Status status) {
        if(mDbRequestId == request_id){
            Log.e("TAG", "DB onChangeStatus");
        }
        if (mShotsRequestId == request_id) {
            Log.e("TAG", "SHOTS onChangeStatus");
        }
    }

    @Override
    public void onRefresh() {
        mDbModel.load();
//        rvShots.setLayoutManager(new GridLayoutManager(DribbleApplication.APP_CONTEXT,2));
    }
}
