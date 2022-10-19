package broker.Topic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

public class Topic {
    private String topicName;

    private int offset;

    /** Contains all the uniqueIds of the messages **/
    private LinkedHashSet<String> messageUIds;

    /** Contains the topic messages with the 'offset' associated to each message **/
    private HashMap<Integer, String> topicMessages;

    /** Contains all the uniqueIds of the clients **/
    private List<String> clientIDs;

    /** If in the future we want to implement High WaterMark, we just need to keep a list of the offsets and the given
        subscriber name
    **/

    /**
     * @brief This constructor is for when a new topic is created
     *
     * @param topicName - topic name, attributed when client is subscribing
     */
    public Topic(String topicName) {
        this.topicName = topicName;
        this.offset = -1; /** Starts at -1, since when the first message will have offset equal to 0 **/
        this.messageUIds = new LinkedHashSet<>();
        this.topicMessages = new HashMap<>();
        this.clientIDs = new ArrayList<>();
    }

    /**
     * @brief This constructor is to reset the topic state, if the broker fails
     *
     * @param topicName - topic name, attributed when client is subscribing
     * @param offset - counter with the id of the latest message
     * @param messageUIds - topic messages unique ids
     * @param topicMessages - topic messages, and correspondent offsets
     */
    public Topic(String topicName, int offset, LinkedHashSet<String> messageUIds, HashMap<Integer, String> topicMessages) {
        this.topicName = topicName;
        this.offset = offset;
        this.messageUIds = messageUIds;
        this.topicMessages = topicMessages;
    }

    /**
     * @param messageUID - message unique id
     * @return True if message is already in topic, False otherwise
     */
    public boolean isMessageInTopic(String messageUID) {
        return messageUIds.contains(messageUID);
    }

    private void incrementOffset() {
        offset++;
    }

    public int getOffset() {
        return offset;
    }

    public String getTopicName() {
        return topicName;
    }

    public List<String> getClientIDs() {
        return clientIDs;
    }

    public void addClient(String clientId) {
        this.clientIDs.add(clientId);
    }

    public void removeClient(String clientId) {
        this.clientIDs.remove(clientId);
    }

    /**
     * @brief Insert a message into the topic
     * @param messageUID - message unique id
     * @param message - message content
     */
    public void insertMessageInTopic(String messageUID, String message) {
        incrementOffset();

        messageUIds.add(messageUID);

        topicMessages.put(offset, message);
    }
}
