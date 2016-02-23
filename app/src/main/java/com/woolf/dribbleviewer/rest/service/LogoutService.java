package com.woolf.dribbleviewer.rest.service;

import retrofit2.http.Header;
import retrofit2.http.POST;
import ru.msu.app.entrant.rest.Urls;
import ru.msu.app.entrant.rest.results.RequestResult;
import rx.Observable;

public interface LogoutService {
    @POST(Urls.LOGOUT)
    Observable<RequestResult> logout(@Header("X-ApiKey") String token);
}
