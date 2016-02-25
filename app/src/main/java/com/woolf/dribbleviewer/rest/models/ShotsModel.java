package com.woolf.dribbleviewer.rest.models;

import android.util.Log;

import com.woolf.dribbleviewer.DribbleApplication;
import com.woolf.dribbleviewer.db.DribbleDatabaseHelper;
import com.woolf.dribbleviewer.models.ShotData;
import com.woolf.dribbleviewer.rest.ApiFactory;
import com.woolf.dribbleviewer.rest.params.RequestParams;
import com.woolf.dribbleviewer.rest.results.Pair;
import com.woolf.dribbleviewer.rest.service.ShotsService;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShotsModel extends BaseModel {

    private Observable<Pair<List<ShotData>>> mObservable;
    private int mPage;

    public ShotsModel(int requestId) {
        super(requestId);
    }

    private Pair<List<ShotData>> saveList(List<ShotData> result) {
        if (mPage == 1) {
            DribbleDatabaseHelper.getInstance(DribbleApplication.APP_CONTEXT).clearShotsTabe();
        }
        DribbleDatabaseHelper.getInstance(DribbleApplication.APP_CONTEXT).addValues(result);
        return new Pair<>(result, null);
    }

    private List<ShotData> filter(List<ShotData> shotDataList) {
        ArrayList<ShotData> result = new ArrayList<>();
        for (ShotData data : shotDataList) {
            if (result.size() < 50) {
                if (!data.isAnimated()) {
                    result.add(data);
                }
            } else {
                break;
            }
        }
        return result;
    }

    private void createObservable(int page) {
        ShotsService service = ApiFactory.shots();
        if (mObservable == null) {
            mPage = page;
            mObservable = service.getShots(RequestParams.getShotsParams(page))
                    .map(this::filter)
                    .map(this::saveList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .cache();
        }
    }

    public void load(int page) {
        onStart();
        createObservable(page);
        unsubscribe();
        subscribe();
    }

    @Override
    protected void subscribe() {
        mObservable.subscribe(this::success, this::error);
    }

    @Override
    protected void clearRequestParams() {
        super.clearRequestParams();
        mObservable = null;
    }

    private void success(Pair<List<ShotData>> user) {
        Log.e("REST","Success");
        onCompleted(user);
        clearRequestParams();
    }

    private void error(Throwable throwable) {
        Log.e("REST","error");
        onError(throwable);
        clearRequestParams();
    }
}
