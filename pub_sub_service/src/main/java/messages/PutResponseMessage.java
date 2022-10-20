package messages;

import com.google.gson.Gson;

public class PutResponseMessage extends Message {
    private final Boolean error;
    private final String error_message;

    public PutResponseMessage(Boolean error, String error_message) {
        super(MessageType.PUT_RESPONSE);
        this.error_message = error_message;
        this.error = error;
    }

    public Boolean getError() {
        return error;
    }

    public String getError_message() {
        return error_message;
    }

    @Override
    public String messageToJson() {
        return (new Gson()).toJson(this);
    }
}
