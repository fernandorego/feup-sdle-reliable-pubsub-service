package messages;

import com.google.gson.Gson;

public class SubscribeMessage extends Message {
    private final String id;

    public SubscribeMessage(String id, String topic) {
        super(topic, MessageType.SUBSCRIBE);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String messageToJson() {
        return (new Gson()).toJson(this);
    }
}
