package com.woolf.dribbleviewer.rest.params;

import com.woolf.dribbleviewer.data.ShotData;

import java.util.HashMap;


public class RequestParams {

    private static final int PAGE = 1;
    private static final int COUNT_SHOTS = 50;

    public static HashMap<String, String> getShotsParams() {
        HashMap<String, String> params = new HashMap<>(6);
        params.put("page", Integer.toString(PAGE));
        params.put("per_page", Integer.toString(COUNT_SHOTS));
        params.put("sort", ShotData.SORT_RECENT);

        return params;

    }



}
