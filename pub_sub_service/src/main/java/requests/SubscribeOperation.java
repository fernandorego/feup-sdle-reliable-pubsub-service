package requests;

public class SubscribeOperation extends Operation {
    private final String id;

    public SubscribeOperation(String id, String topic) {
        super(topic, OperationType.SUBSCRIBE);
        this.id = id;
    }

    @Override
    public String requestToJson() {
        return null;
    }
}
