package messages;

import com.google.gson.Gson;

public class GetResponseMessage extends Message{
    private final Boolean error;
    private final String error_message;
    private final String message;
    private final long offset;

    public GetResponseMessage(Boolean error, String error_message, String message, long offset) {
        super(MessageType.GET_RESPONSE);
        this.error = error;
        this.error_message = error_message;
        this.message = message;
        this.offset = offset;
    }

    public Boolean getError() {
        return error;
    }

    public String getError_message() {
        return error_message;
    }

    public String getMessage() {
        return message;
    }

    public long getOffset() {
        return offset;
    }

    @Override
    public String messageToJson() {
        return (new Gson()).toJson(this);
    }
}
