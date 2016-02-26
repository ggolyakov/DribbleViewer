package com.woolf.dribbleviewer.main;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.woolf.dribbleviewer.DribbleApplication;
import com.woolf.dribbleviewer.R;
import com.woolf.dribbleviewer.base.BaseActivity;
import com.woolf.dribbleviewer.models.ShotData;
import com.woolf.dribbleviewer.rest.listeners.IRestObserver;
import com.woolf.dribbleviewer.rest.listeners.Status;
import com.woolf.dribbleviewer.rest.managers.ErrorHandler;
import com.woolf.dribbleviewer.rest.models.DataBaseModel;
import com.woolf.dribbleviewer.rest.models.ShotsModel;
import com.woolf.dribbleviewer.rest.params.Constants;
import com.woolf.dribbleviewer.rest.results.Pair;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShotListActivity extends BaseActivity implements IRestObserver, SwipeRefreshLayout.OnRefreshListener {

    private static final String SHOT_LIST = "MainActivity.SHOT_LIST";
    private static final String PAGE = "MainActivity.PAGE";

    @Bind(R.id.rv_main)
    RecyclerView rvShots;
    @Bind(R.id.srl_main)
    SwipeRefreshLayout srlReload;

    @Bind(R.id.rl_progress)
    RelativeLayout rlProgress;
    @Bind(R.id.pb_load)
    ProgressBar pbLoad;
    @Bind(R.id.ll_progress_error)
    LinearLayout llError;
    @Bind(R.id.tv_progress_error)
    TextView tvError;

    private ShotsModel mShotsModel;
    private DataBaseModel mDbModel;

    private ArrayList<ShotData> mShotList;

    private ShotsListAdapter mListAdapter;

    private int mPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_list);
        ButterKnife.bind(this);
        loadFromSavedInstanceState(savedInstanceState);
        getShotList();

        subscribe();
        initAdapters();
        setListeners();

    }

    private void loadFromSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mShotList = savedInstanceState.getParcelableArrayList(SHOT_LIST);
            mPage = savedInstanceState.getInt(PAGE);
        }
    }

    @OnClick(R.id.rl_progress)
    void reload() {
        mShotsModel.load(mPage);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(SHOT_LIST, mShotList);
        outState.putInt(PAGE, mPage);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mShotList = savedInstanceState.getParcelableArrayList(SHOT_LIST);
        mPage = savedInstanceState.getInt(PAGE);
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void initAdapters() {
        srlReload.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary);

        mListAdapter = new ShotsListAdapter(getShotList());
        GridLayoutManager manager = new GridLayoutManager(DribbleApplication.APP_CONTEXT, getColumn());

        rvShots.setAdapter(mListAdapter);
        rvShots.setLayoutManager(manager);
    }

    private void reloadList() {
        mPage = 1;
        mShotsModel.load(mPage);
    }

    private void setRefreshing(boolean isRefreshing){
        srlReload.post(() -> srlReload.setRefreshing(isRefreshing));
    }

    private void fillList(ArrayList<ShotData> data) {
        getShotList().addAll(data);
        mListAdapter.notifyDataSetChanged();
    }

    private void clearList() {
        getShotList().clear();
        mListAdapter.notifyDataSetChanged();
    }

    private ArrayList<ShotData> getShotList() {
        if (mShotList == null) {
            mShotList = new ArrayList<>();
        }
        return mShotList;
    }


    private void subscribe() {
        mShotsModel = getRequestFragment().getShotsModel();
        mDbModel = getRequestFragment().getDBModel();

        mShotsModel.addObserver(this);
        mDbModel.addObserver(this);
    }

    private void unsubscribe() {
        mShotsModel.deleteObserver(this);
        mDbModel.deleteObserver(this);
    }

    private int getColumn() {
        return getResources().getInteger(R.integer.shots_column);
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
        if (Constants.DB_REQUEST_ID == request_id) {
            showProgressBar();
        }
        if (Constants.SHOTS_REQUEST_ID == request_id) {
            showProgressBar();
        }

    }

    private void showProgressBar() {
        if (mShotList == null || mShotList.isEmpty()) {
            rlProgress.setVisibility(View.VISIBLE);
            pbLoad.setVisibility(View.VISIBLE);
            llError.setVisibility(View.GONE);
        }else {
            setRefreshing(true);
            rlProgress.setVisibility(View.GONE);
            llError.setVisibility(View.GONE);
        }
    }

    private void showErrorMessage(String error) {
        setRefreshing(false);
        rlProgress.setVisibility(View.VISIBLE);
        pbLoad.setVisibility(View.GONE);
        llError.setVisibility(View.VISIBLE);
        tvError.setText(error);
    }

    private void goneProgressBar() {
        rvShots.setVisibility(View.VISIBLE);
        rlProgress.setVisibility(View.GONE);
        setRefreshing(false);
    }

    @Override
    public void onCompleted(int request_id, Pair object) {
        if (Constants.DB_REQUEST_ID == request_id) {
            if(object.getValue() == null){
                mShotsModel.load(mPage);
            }else {
                ArrayList<ShotData> data = (ArrayList<ShotData>) object.getValue();
                fillList(data);
                goneProgressBar();
            }
        }
        if (Constants.SHOTS_REQUEST_ID == request_id) {
            if(mPage == 1){
                clearList();
            }
            ArrayList<ShotData> data = (ArrayList<ShotData>) object.getValue();
            fillList(data);
            mPage++;
            goneProgressBar();
        }
    }

    @Override
    public void onError(int request_id, ErrorHandler error) {
        if (Constants.DB_REQUEST_ID == request_id) {
            reloadList();
        }
        if (Constants.SHOTS_REQUEST_ID == request_id) {
            showErrorMessage(error.getMessage());
        }
    }

    @Override
    public void onChangeStatus(int request_id, Status status) {

        if (Constants.DB_REQUEST_ID == request_id) {
            switch (status) {
                case DO_NO_LOAD:
                    mDbModel.load();
                    break;
                case LOADING_IS_COMPLETE:
                    if (getShotList().isEmpty()) {
                        reloadList();
                    }
                    break;

                case IN_PROGRESS:
                    showProgressBar();
                    break;

                case LOADING_ERROR:

                    break;
            }
        }
        if (Constants.SHOTS_REQUEST_ID == request_id) {
            switch (status) {
                case DO_NO_LOAD:

                    break;
                case LOADING_IS_COMPLETE:

                    break;

                case IN_PROGRESS:
                    showProgressBar();
                    break;

                case LOADING_ERROR:

                    break;
            }
        }
    }

    @Override
    public void onRefresh() {
        reloadList();
    }
}
