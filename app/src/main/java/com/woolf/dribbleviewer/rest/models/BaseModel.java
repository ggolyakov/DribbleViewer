package com.woolf.dribbleviewer.rest.models;

import com.woolf.dribbleviewer.rest.listeners.IRestObserver;
import com.woolf.dribbleviewer.rest.listeners.Status;
import com.woolf.dribbleviewer.rest.managers.ErrorHandler;
import com.woolf.dribbleviewer.rest.results.Pair;

import java.util.ArrayList;

import rx.Subscription;
import rx.subscriptions.Subscriptions;


public abstract class BaseModel {

    protected Subscription mSubscription = Subscriptions.empty();

    protected int mRequestId;
    protected Status mStatus = Status.DO_NO_LOAD;

    private ArrayList<IRestObserver> mRestObservers = new ArrayList<>();

    public BaseModel(int requestId) {
        mRequestId =requestId;
    }

    protected abstract void subscribe();


    public void addObserver(IRestObserver observer) {
        if (observer == null) {
            throw new NullPointerException("observer == null");
        }
        synchronized (this) {
            if (!mRestObservers.contains(observer))
                mRestObservers.add(observer);
        }
        notifyChangeStatus();
    }

    public synchronized void deleteObserver(IRestObserver observer) {
        mRestObservers.remove(observer);
    }

    /**
     * Removes all observers from the list of observers.
     */
    public synchronized void deleteObservers() {
        mRestObservers.clear();
    }


    protected void clearRequestParams() {
        unsubscribe();
    }


    public void unsubscribe() {
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    protected synchronized void notifyStartLoading() {
        for (IRestObserver observer : mRestObservers) {
            observer.onStartLoading(mRequestId);
        }
    }

    protected synchronized void notifyError(ErrorHandler errorHandler) {
        for (IRestObserver observer : mRestObservers) {
            observer.onError(mRequestId, errorHandler);
        }
    }

    protected synchronized void notifyCompleted(Pair pair) {
        for (IRestObserver observer : mRestObservers) {
            observer.onCompleted(mRequestId, pair);
        }
    }

    protected synchronized void notifyChangeStatus() {
        for (IRestObserver observer : mRestObservers) {
            observer.onChangeStatus(mRequestId, mStatus);
        }
    }

    protected void onStart() {
        mStatus = Status.IN_PROGRESS;
        notifyStartLoading();
    }

    protected void onCompleted(Pair pair) {
        mStatus = Status.LOADING_IS_COMPLETE;
        notifyCompleted(pair);
    }

    protected void onError(Throwable throwable) {
        mStatus = Status.LOADING_ERROR;
        notifyError(new ErrorHandler(throwable));
    }


}
