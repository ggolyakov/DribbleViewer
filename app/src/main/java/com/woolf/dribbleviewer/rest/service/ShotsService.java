package com.woolf.dribbleviewer.rest.service;

import com.woolf.dribbleviewer.data.ShotData;
import com.woolf.dribbleviewer.rest.Urls;

import java.util.HashMap;
import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface ShotsService {
    @GET(Urls.SHOTS)
    Observable<List<ShotData>> getShots(@QueryMap HashMap<String, String> params);
}
