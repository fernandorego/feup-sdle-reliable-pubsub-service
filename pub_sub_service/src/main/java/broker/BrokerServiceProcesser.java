package broker;

import broker.Topic.Topic;
import messages.*;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BrokerServiceProcesser {
    private final List<Topic> topics;

    public BrokerServiceProcesser(List<Topic> topics) {
        this.topics = topics;
    }

    public Message subscribeMessageProcess(SubscribeMessage message) {
        Topic topic = containsTopic(message.getTopic());
        if (topic == null) {
            topic = new Topic(message.getTopic());
            this.topics.add(topic);
        }
        if (topic.getClientIDs().contains(message.getClientId())) {
            System.err.println("Client with id: " + message.getClientId() + " is already subscribed to the topic: " + message.getTopic());
            return new SubscribeResponseMessage(true,"Client is already subscribed to the topic: " + message.getTopic(), -1);
        }
        topic.addClient(message.getClientId());
        System.out.println("Client with id: " + message.getClientId() + " subscribed topic: " + message.getTopic());
        return new SubscribeResponseMessage(false,"", topic.getOffset() + 1);
    }

    public Message unsubscribeMessageProcess(UnsubscribeMessage message) {
        Topic topic = containsTopic(message.getTopic());
        if (topic == null) {
            System.err.println("Client with id: " + message.getClientId() + " tried to unsubscribe a topic that does not exist: " + message.getTopic());
            return new UnsubscribeResponseMessage(true,"Topic does not exist: " + message.getTopic());
        }
        if (!topic.getClientIDs().contains(message.getClientId())) {
            System.err.println("Client with id: " + message.getClientId() + " is already not subscribed to the topic: " + message.getTopic());
            return new UnsubscribeResponseMessage(true,"Client is already not subscribed to the topic: " + message.getTopic());
        }
        topic.removeClient(message.getClientId());
        System.out.println("Client with id: " + message.getClientId() + " unsubscribed topic: " + message.getTopic());
        return new UnsubscribeResponseMessage(false,"");
    }

    public Message putMessageProcess(PutMessage message) {
        Topic topic = containsTopic(message.getTopic());
        String error;
        if (topic == null) {
            error = "Topic " + message.getTopic() + " does not exist to publish a message";
            System.err.println(error);
            return new PutResponseMessage(true, error);
        }

        //TODO: fault tolerance of equal messages with the same uid

        topic.insertMessageInTopic("", message.getMessage());
        System.out.println("A new message was added to topic " + message.getTopic());
        for (Map.Entry<Long,String> entry: topic.getTopicMessages().entrySet()) {
            System.out.println("Key = " + entry.getKey() + " ; Val = " + entry.getValue());
        }
        return new PutResponseMessage(false,"");
    }

    private Topic containsTopic(String topicName) {
        for (Topic t: topics) { if (t.getTopicName().equals(topicName)) { return t; } }
        return null;
    }
}
