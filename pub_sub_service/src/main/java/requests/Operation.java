package requests;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public abstract class Operation {
    public final String topic;
    public final OperationType type;
    public Operation(String topic, OperationType type) {
        this.topic = topic;
        this.type = type;
    }

    protected OperationType getType() {
        return this.type;
    }
    protected abstract String requestToJson();

    static public Operation jsonToRequest(String json) {
        return switch (parseType(json)) {
            case "GET" -> (new Gson()).fromJson(json, GetOperation.class);
            case "PUT" -> (new Gson()).fromJson(json, PutOperation.class);
            case "SUBSCRIBE" -> (new Gson()).fromJson(json, SubscribeOperation.class);
            case "UNSUBSCRIBE" -> (new Gson()).fromJson(json, UnsubscribeOperation.class);
            default -> null;
        };
    }

    static public String parseType(String json) {
        return (new Gson()).fromJson(json, JsonObject.class).get("type").getAsString();
    }
}
