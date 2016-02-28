package com.woolf.dribbleviewer.rest.models;

import com.woolf.dribbleviewer.DribbleApplication;
import com.woolf.dribbleviewer.db.DribbleDatabaseHelper;
import com.woolf.dribbleviewer.models.ShotData;
import com.woolf.dribbleviewer.rest.params.Constants;
import com.woolf.dribbleviewer.rest.results.Pair;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DataBaseModel extends BaseModel {
    private Observable<Pair<List<ShotData>>> mObservable;

    public DataBaseModel(int requestId) {
        super(requestId);
    }

    private Pair<List<ShotData>> convert(List<ShotData> list){
        return new Pair<>(list, Constants.SUCCESS);
    }


    private void createObservable() {
        if (mObservable == null) {
            mObservable = DribbleDatabaseHelper.getInstance(DribbleApplication.APP_CONTEXT)
                    .loadFromDatabase()
                    .map(this::convert)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .cache();
        }
    }

    public void load() {
        onStart();
        createObservable();
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

    private void success(Pair<List<ShotData>> shotList){
        onCompleted(shotList);
        clearRequestParams();
    }

    private void error(Throwable throwable) {
        onError(throwable);
        clearRequestParams();
    }
}
