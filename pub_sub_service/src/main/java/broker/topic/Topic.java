package broker.topic;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class Topic {
    private String topicName;

    private long offset;

    /** Contains all the uniqueIds of the messages **/
    private HashMap<Long, String> messageUIds;

    /** Contains the topic messages with the 'offset' associated to each message **/
    private HashMap<Long, String> topicMessages;

    /** Contains all the uniqueIds of the clients **/
    private HashMap<String, Long> clientIDs;

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
        this.messageUIds = new HashMap<>();
        this.topicMessages = new HashMap<>();
        this.clientIDs = new HashMap<>();
    }

    /**
     * @brief This constructor is to reset the topic state, if the broker fails
     *
     * @param topicName - topic name, attributed when client is subscribing
     * @param offset - counter with the id of the latest message
     * @param messageUIds - topic messages unique ids
     * @param topicMessages - topic messages, and correspondent offsets
     */
    public Topic(String topicName, long offset, HashMap<Long, String> messageUIds, HashMap<Long, String> topicMessages, HashMap<String, Long> clientIDs) {
        this.topicName = topicName;
        this.offset = offset;
        this.messageUIds = messageUIds;
        this.topicMessages = topicMessages;
        this.clientIDs = clientIDs;
    }

    /**
     * @param messageUID - message unique id
     * @return True if message is already in topic, False otherwise
     */
    public boolean isMessageInTopic(String messageUID) {
        return messageUIds.containsValue(messageUID);
    }

    private void incrementOffset() {
        offset++;
    }

    public long getOffset() {
        return offset;
    }

    public String getTopicName() {
        return topicName;
    }

    public HashMap<Long, String> getTopicMessages() {
        return topicMessages;
    }

    public HashMap<String, Long> getClientIDs() {
        return clientIDs;
    }

    public void addClient(String clientId, long offset) {
        this.clientIDs.put(clientId, offset);
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

        messageUIds.put(offset, messageUID);

        topicMessages.put(offset, message);
    }

    public void deletePastMessages() {
        if (topicMessages.isEmpty() || clientIDs.isEmpty()) { return; }
        long lowest_offset = Long.MAX_VALUE;

        for (long offset : clientIDs.values()) {
            if (offset < lowest_offset) {
                lowest_offset = offset;
            }
        }
        long finalLowest_offset = lowest_offset;
        topicMessages.entrySet().removeIf(entry -> finalLowest_offset > entry.getKey());
        messageUIds.entrySet().removeIf(entry -> finalLowest_offset > entry.getKey());
    }
}
