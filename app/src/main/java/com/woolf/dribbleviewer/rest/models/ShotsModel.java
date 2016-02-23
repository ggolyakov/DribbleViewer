package com.woolf.dribbleviewer.rest.models;

import com.woolf.dribbleviewer.data.ShotData;
import com.woolf.dribbleviewer.rest.ApiFactory;
import com.woolf.dribbleviewer.rest.results.Pair;
import com.woolf.dribbleviewer.rest.service.ShotsService;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShotsModel extends BaseModel {
    private Observable<Pair<List<ShotData>>> mObservable;

    public ShotsModel() {
        super();
    }

    private Pair<List<ShotData>> saveUserModel(List<ShotData> result) {
        result.toString();
        return new Pair<>(null, null);
    }

    private void createObservable(HashMap<String, String> params) {
        ShotsService service = ApiFactory.shots();
        if (mObservable == null) {
            mObservable = service.getShots(params)
                    .map(this::saveUserModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .cache();
        }
    }

    public void load(HashMap<String, String> params) {
        onStart();
        createObservable(params);
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
        onCompleted(user);
        clearRequestParams();
    }

    private void error(Throwable throwable) {
        onError(throwable);
        clearRequestParams();
    }
}
