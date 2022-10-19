package broker;

import broker.Topic.Topic;
import messages.Message;
import messages.SubscribeMessage;
import messages.SubscribeResponseMessage;

import java.util.List;

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
        topic.addClient(message.getClientId());
        return new SubscribeResponseMessage(false,"");
    }

    private Topic containsTopic(String topicName) {
        for (Topic t: topics) { if (t.getTopicName().equals(topicName)) { return t; } }
        return null;
    }
}
