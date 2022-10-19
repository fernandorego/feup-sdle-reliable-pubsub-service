package messages;

import com.google.gson.Gson;
import utils.FileUtils;

import java.io.IOException;


public class PutMessage extends Message {
    private final String message;
    private final String topic;

    public PutMessage(String topic, String file_path) throws IOException {
        super(MessageType.PUT);
        this.topic = topic;
        this.message = FileUtils.fileToString(file_path);
    }

    public String getMessage() {
        return message;
    }

    public String getTopic() {
        return topic;
    }

    @Override
    public String messageToJson() {
        return (new Gson()).toJson(this);
    }
}
