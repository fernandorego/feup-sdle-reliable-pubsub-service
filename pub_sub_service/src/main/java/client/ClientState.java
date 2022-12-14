package client;

import com.google.gson.Gson;

public class ClientState {
    private final String topic;
    private long offset;

    public ClientState(String topic, long offset) {
        this.topic = topic;
        this.offset = offset;
    }

    public long getOffset() {
        return offset;
    }

    public String getTopic() {
        return topic;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public String stateToJson() {
        return (new Gson()).toJson(this);
    }
    public static ClientState jsonToState(String json) {
        return (new Gson()).fromJson(json, ClientState.class);
    }
}
