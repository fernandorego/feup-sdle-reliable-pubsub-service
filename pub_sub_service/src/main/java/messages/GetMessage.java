package messages;

import com.google.gson.Gson;

public class GetMessage extends Message {
    private final String clientId;
    private final String topic;
    private final long offset;

    public GetMessage(String topic, String clientId) {
        super( MessageType.GET);
        this.clientId = clientId;
        this.topic = topic;
        this.offset = 0; //TODO: get offset from file
    }

    public String getClientId() {
        return clientId;
    }

    public String getTopic() {
        return topic;
    }

    public long getOffset() {
        return offset;
    }

    @Override
    public String messageToJson() {
        return (new Gson()).toJson(this);
    }
}
