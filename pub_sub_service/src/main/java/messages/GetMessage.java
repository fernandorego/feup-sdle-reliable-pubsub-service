package messages;

import client.ClientState;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GetMessage extends Message {
    private final static String CLIENT_DIR_PATH = "./client/";
    private final String clientId;
    private final String topic;
    private final long offset;

    public GetMessage(String topic, String clientId) {
        super(MessageType.GET);
        this.clientId = clientId;
        this.topic = topic;
        this.offset = getTopicOffset();
    }

    public String getClientId() {
        return clientId;
    }

    public String getTopic() {
        return topic;
    }

    public long getOffset() {
        return offset;
    }

    @Override
    public String messageToJson() {
        return (new Gson()).toJson(this);
    }

    private long getTopicOffset() {
        String file_path = CLIENT_DIR_PATH + this.clientId;
        try (BufferedReader br = new BufferedReader(new FileReader(file_path))) {
            String json;
            while ((json = br.readLine()) != null) {
                ClientState clientState = ClientState.jsonToState(json);
                if (clientState.getTopic().equals(this.topic)) {
                    return clientState.getOffset();
                }
            }
            return -1;
        } catch (IOException e) {
            return -1;
        }
    }
}
