package messages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public abstract class Message {
    protected final String topic;
    protected final MessageType type;

    private static final Map<String, Type> operationMap = new HashMap<>() {
        {
            put("GET", GetMessage.class);
            put("PUT", PutMessage.class);
            put("SUBSCRIBE", SubscribeMessage.class);
            put("UNSUBSCRIBE", UnsubscribeMessage.class);
        }
    };

    public Message(String topic, MessageType type) {
        this.topic = topic;
        this.type = type;
    }

    public abstract String messageToJson();

    public String getTopic() {
        return topic;
    }

    public MessageType getType() {
        return type;
    }

    static public Message jsonToRequest(String json) {
        return (new Gson()).fromJson(json, operationMap.get(parseType(json)));
    }

    static public String parseType(String json) {
        return (new Gson()).fromJson(json, JsonObject.class).get("type").getAsString();
    }
}
