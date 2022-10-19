package client;

import messages.Message;
import messages.SubscribeMessage;
import messages.SubscribeResponseMessage;
import utils.FileUtils;

import java.io.IOException;

public class ClientServiceProcesser {
    private final static String CLIENT_DIR_PATH = "./client/";
    private final SubscribeMessage message;
    public ClientServiceProcesser(Message message) {
        this.message = (SubscribeMessage) message;
    }

    public void subscribeResponseMessageProcess(SubscribeResponseMessage replyMessage) {
        if (replyMessage.getError()) {
            System.err.println(replyMessage.getError_message());
            return;
        }
        FileUtils.createDir(CLIENT_DIR_PATH);
        String file_path = CLIENT_DIR_PATH + message.getClientId();
        try {
            FileUtils.createFile(CLIENT_DIR_PATH + message.getClientId());
        } catch (IOException e) {
            System.out.println(e.toString());
            System.exit(1);
        }
        FileUtils.appendStringToFile((new ClientState(message.getTopic(),replyMessage.getOffset())).stateToJson() + "\n", file_path);
    }
}
