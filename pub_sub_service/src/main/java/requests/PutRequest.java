package requests;

import com.google.gson.Gson;

public class PutRequest extends Request {
    private final String message;

    public PutRequest(String topic, String message) {
        super(topic, RequestType.PUT);
        this.message = message;
    }

    @Override
    public String requestToJson() {
        return (new Gson()).toJson(this);
    }
}
