package com.woolf.dribbleviewer.rest.params;

import com.woolf.dribbleviewer.models.ShotData;

import java.util.HashMap;


public class RequestParams {

    private static final int COUNT_SHOTS = 10;

    public static HashMap<String, String> getShotsParams(int page) {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("page", Integer.toString(page));
        params.put("per_page", Integer.toString(COUNT_SHOTS));
        params.put("sort", ShotData.SORT_RECENT);

        return params;

    }



}
