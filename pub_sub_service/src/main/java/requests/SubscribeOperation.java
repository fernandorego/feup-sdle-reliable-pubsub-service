package requests;

import com.google.gson.Gson;

public class SubscribeOperation extends Operation {
    private final String id;

    public SubscribeOperation(String id, String topic) {
        super(topic, OperationType.SUBSCRIBE);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String requestToJson() {
        return (new Gson()).toJson(this);
    }
}
