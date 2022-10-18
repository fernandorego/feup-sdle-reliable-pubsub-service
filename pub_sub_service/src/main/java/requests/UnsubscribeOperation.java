package requests;

public class UnsubscribeOperation extends Operation {
    private final String id;
    public UnsubscribeOperation(String id, String topic) {
        super(topic, OperationType.UNSUBSCRIBE);
        this.id = id;
    }

    @Override
    public String requestToJson() {
        return null;
    }
}
