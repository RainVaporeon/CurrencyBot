package com.spiritlight.currencybot.connection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.spiritlight.currencybot.connection.Type.JSON;

@SuppressWarnings("ConstantConditions")
public class Connection {
    private static final OkHttpClient client = new OkHttpClient()
            .newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .cache(null)
            .build();

    public static String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {

            return response.body() == null ? "null" : response.body().string();
        }
    }

    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body() == null ? "null" : response.body().string();
        }
    }

}
