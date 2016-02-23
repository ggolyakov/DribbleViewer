package com.woolf.dribbleviewer.rest.service;

import java.util.HashMap;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PATCH;
import ru.msu.app.entrant.rest.Urls;
import ru.msu.app.entrant.rest.results.AuthenticatedUserResult;
import ru.msu.app.entrant.rest.results.RequestResult;
import rx.Observable;

public interface UpdateProfileService {
    @FormUrlEncoded
    @PATCH(Urls.UPDATE_PROFILE)
    Observable<RequestResult<AuthenticatedUserResult>> updateProfile(@FieldMap HashMap<String, String> params);
}
