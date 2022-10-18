package client.requests;

public class SubscribeRequest implements Request {
    private final String id;
    private final String topic;

    public SubscribeRequest(String id, String topic) {
        this.id = id;
        this.topic = topic;
    }

    @Override
    public String getType() {
        return "SUBSCRIBE";
    }

    @Override
    public String requestToJson() {
        return null;
    }
}
