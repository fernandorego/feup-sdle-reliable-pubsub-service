package requests;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public abstract class Operation {
    protected final String topic;
    protected final OperationType type;

    private static final Map<String, Type> operationMap = new HashMap<>() {
        {
            put("GET", GetOperation.class);
            put("PUT", PutOperation.class);
            put("SUBSCRIBE", SubscribeOperation.class);
            put("UNSUBSCRIBE", UnsubscribeOperation.class);
        }
    };

    public Operation(String topic, OperationType type) {
        this.topic = topic;
        this.type = type;
    }

    protected abstract String requestToJson();

    public String getTopic() {
        return topic;
    }

    public OperationType getType() {
        return type;
    }

    static public Operation jsonToRequest(String json) {
        return (new Gson()).fromJson(json, operationMap.get(parseType(json)));
    }

    static public String parseType(String json) {
        return (new Gson()).fromJson(json, JsonObject.class).get("type").getAsString();
    }
}
