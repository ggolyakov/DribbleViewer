package com.woolf.dribbleviewer.rest;

import android.support.annotation.NonNull;

import com.woolf.dribbleviewer.BuildConfig;
import com.woolf.dribbleviewer.rest.service.ShotsService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {
    private static final int CONNECT_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 30;
    private static final int TIMEOUT = 30;

    private static final OkHttpClient CLIENT = getOkHttpClient(BuildConfig.ACCESS_TOKEN);





    private static OkHttpClient getOkHttpClient(String authToken) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.addInterceptor(chain -> {
            Request request = chain.request();
            Request authRequest = request.newBuilder().addHeader("Authorization", "Bearer " + authToken).build();
            return chain.proceed(authRequest);
        });
        //Тут добавлять SSL
        return builder.build();
    }

    public static ShotsService shots(){
        return getRetrofit().create(ShotsService.class);
    }


    @NonNull
    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Urls.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(CLIENT)
                .build();
    }


}
