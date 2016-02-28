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

    @Override
    protected void onDestroy() {
        unsubscribe();
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        reloadList();
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

    private void loadFromSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mShotList = savedInstanceState.getParcelableArrayList(SHOT_LIST);
            mPage = savedInstanceState.getInt(PAGE);
        }
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


    private void initAdapters() {
        srlReload.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary);

        mListAdapter = new ShotsListAdapter(getShotList());
        GridLayoutManager manager = new GridLayoutManager(DribbleApplication.APP_CONTEXT, getResources().getInteger(R.integer.shots_column));

        rvShots.setAdapter(mListAdapter);
        rvShots.setLayoutManager(manager);
    }

    private void setListeners() {
        srlReload.setOnRefreshListener(this);

    }


    private void reloadList() {
        mPage = 1;
        mShotsModel.load(mPage);
    }


     // https://code.google.com/p/android/issues/detail?id=77712
    private void setRefreshing(boolean isRefreshing){
        srlReload.post(() -> srlReload.setRefreshing(isRefreshing));
    }

    private void fillList(Pair object) {
        ArrayList<ShotData> data = (ArrayList<ShotData>) object.getValue();
        if (mPage == 1) {
            clearList();
        }
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

    @Override
    public void onStartLoading(int request_id) {
        switch (request_id) {
            case Constants.DB_REQUEST_ID:
                showProgressBar();
                break;

            case Constants.SHOTS_REQUEST_ID:
                showProgressBar();
                break;
        }

    }

    @Override
    public void onCompleted(int request_id, Pair object) {
        switch (request_id) {
            case Constants.DB_REQUEST_ID:
                if (object.getValue() == null) {
                    mShotsModel.load(mPage);
                } else {
                    fillList(object);
                    goneProgressBar();
                }
                break;

            case Constants.SHOTS_REQUEST_ID:
                fillList(object);
                goneProgressBar();
                mPage++;
                break;
        }
    }

    @Override
    public void onError(int request_id, ErrorHandler error) {
        switch (request_id) {
            case Constants.DB_REQUEST_ID:
                reloadList();
                break;

            case Constants.SHOTS_REQUEST_ID:
                showErrorMessage(error.getMessage());
                break;
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
            }
        }
        if (Constants.SHOTS_REQUEST_ID == request_id) {
            switch (status) {
                case IN_PROGRESS:
                    showProgressBar();
                    break;
            }
        }
    }

    private void showProgressBar() {
        rlProgress.setVisibility(View.GONE);
        llError.setVisibility(View.GONE);
        if (getShotList().isEmpty()) {
            pbLoad.setVisibility(View.VISIBLE);
        } else {
            setRefreshing(true);
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
        setRefreshing(false);
        rvShots.setVisibility(View.VISIBLE);
        rlProgress.setVisibility(View.GONE);

    }
}
