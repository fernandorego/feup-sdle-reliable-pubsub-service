package client.requests;

public class UnsubscribeRequest implements Request {
    private final String id;
    private final String topic;

    public UnsubscribeRequest(String id, String topic) {
        this.id = id;
        this.topic = topic;
    }

    @Override
    public String getType() {
        return "UNSUBSCRIBE";
    }

    @Override
    public String requestToJson() {
        return null;
    }
}
