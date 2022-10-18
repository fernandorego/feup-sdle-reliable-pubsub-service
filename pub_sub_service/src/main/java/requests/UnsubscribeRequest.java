package requests;

public class UnsubscribeRequest extends Request {
    private final String id;
    public UnsubscribeRequest(String id, String topic) {
        super(topic, RequestType.UNSUBSCRIBE);
        this.id = id;
    }

    @Override
    public String requestToJson() {
        return null;
    }
}
