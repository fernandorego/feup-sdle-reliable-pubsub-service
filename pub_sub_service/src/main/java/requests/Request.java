package client.requests;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import zmq.socket.reqrep.Req;

public abstract class Request {
    public final String topic;
    public final RequestType type;
    public Request(String topic, RequestType type) {
        this.topic = topic;
        this.type = type;
    }

    protected RequestType getType() {
        return this.type;
    }
    protected abstract String requestToJson();

    static public Request jsonToRequest(String json) {
        return switch (parseType(json)) {
            case "GET" -> (new Gson()).fromJson(json, GetRequest.class);
            case "PUT" -> (new Gson()).fromJson(json, PutRequest.class);
            case "SUBSCRIBE" -> (new Gson()).fromJson(json, SubscribeRequest.class);
            case "UNSUBSCRIBE" -> (new Gson()).fromJson(json, UnsubscribeRequest.class);
            default -> null;
        };
    }

    static public String parseType(String json) {
        return (new Gson()).fromJson(json, JsonObject.class).get("type").getAsString();
    }
}
