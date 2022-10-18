package requests;

import com.google.gson.Gson;

public class GetOperation extends Operation {
    private final String id;
    private final long offset;

    public GetOperation(String id, String topic) {
        super(topic, OperationType.GET);
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
    public String requestToJson() {
        return (new Gson()).toJson(this);
    }
}
