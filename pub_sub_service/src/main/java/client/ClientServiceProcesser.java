package client;

import messages.*;
import utils.FileUtils;

import java.io.*;

public class ClientServiceProcesser {
    private final static String CLIENT_DIR_PATH = "./client/";
    private Message message;
    public ClientServiceProcesser(Message message) {
        this.message = message;
    }

    public void subscribeResponseMessageProcess(SubscribeResponseMessage replyMessage) {
        SubscribeMessage subscribeMessage = (SubscribeMessage) message;
        if (replyMessage.getError()) {
            System.err.println(replyMessage.getError_message());
            return;
        }
        FileUtils.createDir(CLIENT_DIR_PATH);
        String file_path = CLIENT_DIR_PATH + subscribeMessage.getClientId();
        try {
            FileUtils.createFile(CLIENT_DIR_PATH + subscribeMessage.getClientId());
        } catch (IOException e) {
            System.out.println(e.toString());
            System.exit(1);
        }
        FileUtils.appendStringToFile((new ClientState(subscribeMessage.getTopic(),replyMessage.getOffset())).stateToJson() + "\n", file_path);
        System.out.println("Client with id: " + subscribeMessage.getClientId() + " subscribed topic: " + subscribeMessage.getTopic());
    }

    public void unsubscribeResponseMessageProcess(UnsubscribeResponseMessage replyMessage) {
        UnsubscribeMessage unsubscribeMessage = (UnsubscribeMessage) message;
        if (replyMessage.getError()) {
            System.err.println(replyMessage.getError_message());
            return;
        }
        String file_path = CLIENT_DIR_PATH + unsubscribeMessage.getClientId();
        try (BufferedReader br = new BufferedReader(new FileReader(file_path))) {
            String json;
            String file = "";
            while ((json = br.readLine()) != null) {
                if (ClientState.jsonToState(json).getTopic().equals(unsubscribeMessage.getTopic())) { continue; }
                file = file.concat(json + "\n");
            }
            FileUtils.stringToFile(file, file_path);
        } catch (IOException e) {
            System.out.println(e.toString());
            System.exit(1);
        }
        System.out.println("Client with id: " + unsubscribeMessage.getClientId() + " unsubscribed topic: " + unsubscribeMessage.getTopic());
    }
}
