package messages;

import com.google.gson.Gson;

public class SubscribeResponseMessage extends Message {
    private final Boolean error;
    private final String error_message;
    private final long offset;

    public SubscribeResponseMessage(Boolean error, String error_message, long offset) {
        super(MessageType.SUBSCRIBE_RESPONSE);
        this.error_message = error_message;
        this.error = error;
        this.offset = offset;
    }

    public Boolean getError() {
        return error;
    }

    public String getError_message() {
        return error_message;
    }

    public long getOffset() {
        return offset;
    }

    @Override
    public String messageToJson() {
        return (new Gson()).toJson(this);
    }
}
