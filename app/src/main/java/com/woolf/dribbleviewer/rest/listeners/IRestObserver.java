package com.woolf.dribbleviewer.rest.listeners;

import com.woolf.dribbleviewer.rest.managers.ErrorHandler;
import com.woolf.dribbleviewer.rest.results.Pair;


public interface IRestObserver {

    void onStartLoading(int request_id);

    void onCompleted(int request_id, Pair object);

    void onError(int request_id, ErrorHandler error);

    void onChangeStatus(int request_id, Status status);


}
