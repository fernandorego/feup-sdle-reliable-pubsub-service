package messages;

import com.google.gson.Gson;
import utils.FileUtils;

import java.io.IOException;


public class PutMessage extends Message {
    private final String message;

    public PutMessage(String topic, String file_path) throws IOException {
        super(topic, MessageType.PUT);
        this.message = FileUtils.fileToString(file_path);
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String messageToJson() {
        return (new Gson()).toJson(this);
    }
}
