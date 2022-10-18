package requests;

public class GetOperation extends Operation {
    private final String id;
    private final long offset;

    public GetOperation(String id, String topic) {
        super(topic, OperationType.GET);
        this.id = id;
        this.offset = 0; //TODO: get offset from file
    }

    @Override
    public String requestToJson() {
        return null;
    }
}
