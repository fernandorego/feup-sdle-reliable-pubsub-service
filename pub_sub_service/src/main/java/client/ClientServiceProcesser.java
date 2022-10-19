package client;

import messages.SubscribeResponseMessage;

public class ClientServiceProcesser {
    public ClientServiceProcesser() {}

    public void subscribeResponseMessageProcess(SubscribeResponseMessage message) {
        System.out.println(message.messageToJson());
    }
}
