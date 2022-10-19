package messages;

import com.google.gson.Gson;

public class UnsubscribeMessage extends Message {
    private final String id;
    public UnsubscribeMessage(String id, String topic) {
        super(topic, MessageType.UNSUBSCRIBE);
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
