package requests;

import com.google.gson.Gson;

public class PutOperation extends Operation {
    private final String message;

    public PutOperation(String topic, String message) {
        super(topic, OperationType.PUT);
        this.message = message;
    }

    @Override
    public String requestToJson() {
        return (new Gson()).toJson(this);
    }
}
