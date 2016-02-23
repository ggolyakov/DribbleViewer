package com.woolf.dribbleviewer.rest.params;

import java.util.HashMap;

/**
 * Created by woolf on 11.02.16.
 */
public class RequestParams {



    public static HashMap<String, String> getRegistrationParams(String name, String familyName,
                                                                String email, String phoneNumber,
                                                                String password, int gradeId) {
        HashMap<String, String> params = new HashMap<>(6);
        params.put("user[name]", name);
        params.put("user[family_name]", familyName);
        params.put("user[email]", email);

        params.put("user[password]", password);
        params.put("user[grade]", String.valueOf(gradeId));
        return params;
    }



}
