package com.spiritlight.currencybot.connection;

import com.spiritlight.currencybot.util.StringUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.util.HashMap;
import java.util.Map;

import static com.spiritlight.currencybot.connection.Type.JSON;

public class RequestDetails {

    private final Map<String, String> headers = new HashMap<>();

    private String body;

    private RequestBody requestBody;

    public RequestDetails() {

    }

    public RequestDetails addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public RequestDetails setBody(String content) {
        this.body = content;
        return this;
    }

    public RequestDetails setRequestBody(String data, MediaType type) {
        this.requestBody = RequestBody.create(data, type);
        return this;
    }

    public String getBody() {
        return StringUtils.parse(body);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public RequestBody getRequestBody() {
        return requestBody == null ? substitute() : requestBody;
    }

    public boolean hasBody() {
        return body != null && body.isEmpty();
    }

    public boolean hasRequestBody() {
        return requestBody != null;
    }

    public boolean hasHeader() {
        return !headers.isEmpty();
    }

    private RequestBody substitute() {
        return body == null ? RequestBody.create("{}", JSON) : RequestBody.create(body, JSON);
    }
}
