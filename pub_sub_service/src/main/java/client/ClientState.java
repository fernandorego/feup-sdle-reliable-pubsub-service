package client;

import com.google.gson.Gson;

public class ClientState {
    private final String topic;
    private final long offset;

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

    public String stateToJson() {
        return (new Gson()).toJson(this);
    }
}
