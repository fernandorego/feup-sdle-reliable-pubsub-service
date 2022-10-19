package messages;

import com.google.gson.Gson;

public class SubscribeMessage extends Message {
    private final String client_id;
    private final String topic;


    public SubscribeMessage(String topic, String client_id) {
        super(MessageType.SUBSCRIBE);
        this.topic = topic;
        this.client_id = client_id;
    }

    public String getClientId() {
        return client_id;
    }

    public String getTopic() {
        return topic;
    }

    @Override
    public String messageToJson() {
        return (new Gson()).toJson(this);
    }
}
