package broker;

import broker.topic.Topic;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import messages.*;
import org.zeromq.ZMQ.Socket;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class BrokerService {
    private Message message;
    private final Socket server;
    private final BrokerServiceProcessor brokerServiceProcessor;

    public BrokerService(Socket server) throws IOException {
        this.message = null;
        this.server = server;
        this.brokerServiceProcessor = new BrokerServiceProcessor(restoreTopics());
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void processMessage() throws IOException {
        Message reply_message;
        if (this.message == null) { return; }
        reply_message = switch (this.message.getType()) {
            case SUBSCRIBE -> brokerServiceProcessor.subscribeMessageProcess((SubscribeMessage) this.message);
            case UNSUBSCRIBE -> brokerServiceProcessor.unsubscribeMessageProcess((UnsubscribeMessage) this.message);
            case PUT -> brokerServiceProcessor.putMessageProcess((PutMessage) this.message);
            case GET -> brokerServiceProcessor.getMessageProcess((GetMessage) this.message);
            default -> null;
        };
        if (reply_message != null) {
            server.send(reply_message.messageToJson());
        } else {
            System.out.println("Processing messages of type " + this.message.getType() + "is not implemented yet");
        }
    }

    private List<Topic> restoreTopics() throws IOException {

        List<Topic> topicList = new ArrayList<>();

        Files.walk(Paths.get("broker/")).filter(file -> !file.toFile().isDirectory()).forEach(f -> {
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                FileReader reader = new FileReader(f.toString());

                Topic topic = gson.fromJson(reader, Topic.class);

                topicList.add(topic);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        return topicList;
    }



}
