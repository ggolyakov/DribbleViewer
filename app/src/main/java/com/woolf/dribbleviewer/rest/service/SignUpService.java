package com.woolf.dribbleviewer.rest.service;

import java.util.HashMap;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import ru.msu.app.entrant.rest.Urls;
import ru.msu.app.entrant.rest.results.RequestResult;
import ru.msu.app.entrant.rest.results.AuthenticatedUserResult;
import rx.Observable;

public interface SignUpService {
    @FormUrlEncoded
    @POST(Urls.SIGN_UP)
    Observable<RequestResult<AuthenticatedUserResult>> registration(@FieldMap HashMap<String, String> params);
}
