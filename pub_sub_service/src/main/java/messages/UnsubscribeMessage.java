package messages;

import com.google.gson.Gson;

public class UnsubscribeMessage extends Message {
    private final String clientId;
    private final String topic;
    public UnsubscribeMessage(String topic, String clientId) {
        super(MessageType.UNSUBSCRIBE);
        this.clientId = clientId;
        this.topic = topic;
    }

    public String getClientId() {
        return clientId;
    }

    public String getTopic() {
        return topic;
    }

    @Override
    public String messageToJson() {
        return (new Gson()).toJson(this);
    }
}
