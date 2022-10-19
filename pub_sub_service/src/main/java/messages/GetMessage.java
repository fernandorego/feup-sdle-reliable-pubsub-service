package messages;

import com.google.gson.Gson;

public class GetMessage extends Message {
    private final String id;
    private final long offset;

    public GetMessage(String id, String topic) {
        super(topic, MessageType.GET);
        this.id = id;
        this.offset = 0; //TODO: get offset from file
    }

    public String getId() {
        return id;
    }

    public long getOffset() {
        return offset;
    }

    @Override
    public String messageToJson() {
        return (new Gson()).toJson(this);
    }
}
