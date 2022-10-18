package client.requests;

public class SubscribeRequest extends Request {
    private final String id;

    public SubscribeRequest(String id, String topic) {
        super(topic, RequestType.SUBSCRIBE);
        this.id = id;
    }

    @Override
    public String requestToJson() {
        return null;
    }
}
