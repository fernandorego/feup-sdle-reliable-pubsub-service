package broker;

import broker.topic.Topic;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import messages.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class BrokerServiceProcessor {
    private final List<Topic> topics;

    private final String baseFilePath = "broker/";

    public BrokerServiceProcessor(List<Topic> topics) {
        this.topics = topics;
    }

    public Message subscribeMessageProcess(SubscribeMessage message) throws IOException{
        Topic topic = containsTopic(message.getTopic());
        if (topic == null) {
            topic = new Topic(message.getTopic());
            this.topics.add(topic);
        }

        if (topic.getClientIDs().containsKey(message.getClientId())) {
            System.err.println("Client with id: " + message.getClientId() + " is already subscribed to the topic: " + message.getTopic());
            return new SubscribeResponseMessage(true,"Client with id: " + message.getClientId() + " is already subscribed to the topic: " + message.getTopic(), topic.getClientIDs().get(message.getClientId()));
        }

        topic.addClient(message.getClientId(), topic.getOffset() + 1);

        saveTopicState(topic);

        System.out.println("Client with id: " + message.getClientId() + " subscribed topic: " + message.getTopic());
        return new SubscribeResponseMessage(false,"", topic.getOffset() + 1);
    }

    public Message unsubscribeMessageProcess(UnsubscribeMessage message) throws IOException {
        Topic topic = containsTopic(message.getTopic());
        if (topic == null) {
            System.err.println("Client with id: " + message.getClientId() + " tried to unsubscribe a topic that does not exist: " + message.getTopic());
            return new UnsubscribeResponseMessage(true,"Topic does not exist: " + message.getTopic());
        }
        if (!topic.getClientIDs().containsKey(message.getClientId())) {
            System.err.println("Client with id: " + message.getClientId() + " is already not subscribed to the topic: " + message.getTopic());
            return new UnsubscribeResponseMessage(true,"Client is already not subscribed to the topic: " + message.getTopic());
        }
        topic.removeClient(message.getClientId());

        saveTopicState(topic);

        System.out.println("Client with id: " + message.getClientId() + " unsubscribed topic: " + message.getTopic());
        return new UnsubscribeResponseMessage(false,"");
    }

    public Message putMessageProcess(PutMessage message) throws IOException{
        Topic topic = containsTopic(message.getTopic());
        String error;

        if (topic == null) {
            error = "Topic: " + message.getTopic() + " does not exist to publish a message";
            System.err.println(error);
            return new PutResponseMessage(true, error);
        }

        if(topic.isMessageInTopic(message.getMessageUID())) {
            error = "Message with UID: " + message.getMessageUID() + " is already in Topic: " + message.getTopic();
            System.err.println(error);
            return new PutResponseMessage(true, error);
        }

        topic.insertMessageInTopic(message.getMessageUID(), message.getMessage());

        saveTopicState(topic);

        System.out.println("A new message was added to topic: " + message.getTopic());
        return new PutResponseMessage(false,"");
    }

    public Message getMessageProcess(GetMessage message) throws IOException {
        Topic topic = containsTopic(message.getTopic());
        String error;
        if (topic == null) {
            error = "Topic: " + message.getTopic() + " does not exist to get a message";
            System.err.println(error);
            return new GetResponseMessage(true, error, "", -1);
        } else if (!topic.getClientIDs().containsKey(message.getClientId())) {
            System.err.println("Client with id: " + message.getClientId() + " is not subscribed to the topic: " + message.getTopic());
            return new GetResponseMessage(true, "Client is not subscribed to the topic: " + message.getTopic(), "", -1);
        }

        long offset = message.getOffset();
        if (offset == -1) {
            offset = topic.getClientIDs().get(message.getClientId());
        } else {
            topic.getClientIDs().put(message.getClientId(), offset);
        }

        topic.deletePastMessages();

        if (!topic.getTopicMessages().containsKey(offset)) {
            System.err.println("There are no more messages from the topic: " + message.getTopic() + " to send to client " + message.getClientId());
            return new GetResponseMessage(true, "There are no more messages from the topic: " + message.getTopic(), "", -1);
        }

        saveTopicState(topic);

        System.out.println("A message from topic " + message.getTopic() + " was sent to client " + message.getClientId());
        return new GetResponseMessage(false,"",topic.getTopicMessages().get(offset), offset);
    }

    private Topic containsTopic(String topicName) {
        for (Topic t: topics) { if (t.getTopicName().equals(topicName)) { return t; } }
        return null;
    }

    private void saveTopicState(Topic topic) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        FileWriter n = new FileWriter(baseFilePath + topic.getTopicName() + ".json");

        gson.toJson(topic,n);

        n.flush();
        n.close();
    }
}
