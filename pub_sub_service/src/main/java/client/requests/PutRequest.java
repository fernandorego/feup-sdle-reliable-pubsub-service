package client.requests;

import com.google.gson.Gson;

public class PutRequest implements Request {
    private final String topic;
    private final String message;

    public PutRequest(String topic, String message) {
        this.topic = topic;
        this.message = message;
    }

    @Override
    public String getType() {
        return "PUT";
    }

    @Override
    public String requestToJson() {
        return (new Gson()).toJson(this);
    }
}
