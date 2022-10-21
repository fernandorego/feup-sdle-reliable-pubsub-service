package client;

import messages.*;
import utils.FileUtils;

import java.io.*;

public class ClientServiceProcesser {
    private final static String CLIENT_DIR_PATH = "./client/";
    private final Message message;
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
        checkClientFile(file_path);
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

    public void putResponseMessageProcess(PutResponseMessage replyMessage) {
        PutMessage putMessage = (PutMessage) message;
        if (replyMessage.getError()) {
            System.err.println(replyMessage.getError_message());
            return;
        }

        System.out.println("New message was added to topic " + putMessage.getTopic());
    }

    public void getResponseMessageProcess(GetResponseMessage replyMessage) {
        GetMessage getMessage = (GetMessage) message;
        if (replyMessage.getError()) {
            System.err.println(replyMessage.getError_message());
            return;
        }

        String file_path = CLIENT_DIR_PATH + getMessage.getClientId();
        checkClientFile(file_path);
        try (BufferedReader br = new BufferedReader(new FileReader(file_path))) {
            String json;
            String file = "";
            boolean checkState = false;
            while ((json = br.readLine()) != null) {
                ClientState clientState = ClientState.jsonToState(json);
                if (clientState.getTopic().equals(getMessage.getTopic())) {
                    checkState = true;
                    clientState.setOffset(replyMessage.getOffset() + 1);
                    file = file.concat(clientState.stateToJson() + "\n");
                } else {
                    file = file.concat(json + "\n");
                }
            }
            if (!checkState) {
                file = file.concat((new ClientState(getMessage.getTopic(),replyMessage.getOffset() + 1)).stateToJson() + "\n");
            }
            FileUtils.stringToFile(file, file_path);
        } catch (IOException e) {
            System.out.println(e.toString());
            System.exit(1);
        }

        System.out.println("Message recieved from topic " + getMessage.getTopic() + ":");
        System.out.println(replyMessage.getMessage());
    }

    private void checkClientFile(String file_path) {
        FileUtils.createDir(CLIENT_DIR_PATH);
        try {
            FileUtils.createFile(file_path);
        } catch (IOException e) {
            System.out.println(e.toString());
            System.exit(1);
        }
    }
}
