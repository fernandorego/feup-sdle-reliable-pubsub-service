package messages;

import com.google.gson.Gson;
import utils.FileUtils;

import java.io.IOException;
import java.time.Instant;


public class PutMessage extends Message {
    private final String message;
    private final String topic;

    private final String messageUID;

    public PutMessage(String topic, String file_path) throws IOException {
        super(MessageType.PUT);
        this.topic = topic;
        this.message = FileUtils.fileToString(file_path);
        this.messageUID = hashMessage(this.message);
    }

    /**
     * Hash the message content and add the epoch entropy to create the fault tolerance schema
     * @return Message Unique ID
     */
    private String hashMessage(String message) throws ArithmeticException{
        String milliSinceEpoch = String.valueOf(Instant.now().toEpochMilli());
        String messageToHash = milliSinceEpoch + message;

        return String.valueOf(messageToHash.hashCode());
    }

    public String getMessage() {
        return message;
    }

    public String getTopic() {
        return topic;
    }

    public String getMessageUID() {
        return messageUID;
    }

    @Override
    public String messageToJson() {
        return (new Gson()).toJson(this);
    }
}
