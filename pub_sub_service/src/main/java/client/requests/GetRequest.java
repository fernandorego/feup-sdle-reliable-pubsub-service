package client.requests;

public class GetRequest implements Request {
    private final String id;
    private final String topic;
    private final long offset;

    public GetRequest(String id, String topic) {
        this.id = id;
        this.topic = topic;

        this.offset = 0; //TODO: get offset from file
    }

    @Override
    public String getType() {
        return "GET";
    }

    @Override
    public String requestToJson() {
        return null;
    }
}
