package requests;

import com.google.gson.Gson;

public class UnsubscribeOperation extends Operation {
    private final String id;
    public UnsubscribeOperation(String id, String topic) {
        super(topic, OperationType.UNSUBSCRIBE);
        this.id = id;
    }

    @Override
    public String requestToJson() {
        return (new Gson()).toJson(this);
    }
}
