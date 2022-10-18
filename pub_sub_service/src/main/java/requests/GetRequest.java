package client.requests;

public class GetRequest extends Request {
    private final String id;
    private final long offset;

    public GetRequest(String id, String topic) {
        super(topic, RequestType.GET);
        this.id = id;
        this.offset = 0; //TODO: get offset from file
    }

    @Override
    public String requestToJson() {
        return null;
    }
}
