package messages;

import com.google.gson.Gson;

public class SubscribeResponseMessage extends Message {
    private final Boolean error;
    private final String error_message;

    public SubscribeResponseMessage(Boolean error, String error_message) {
        super(MessageType.SUBSCRIBE_RESPONSE);
        this.error_message = error_message;
        this.error = error;
    }

    @Override
    public String messageToJson() {
        return (new Gson()).toJson(this);
    }
}
