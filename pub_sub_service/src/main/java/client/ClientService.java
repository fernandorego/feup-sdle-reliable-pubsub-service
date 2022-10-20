package client;

import messages.*;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class ClientService {
    private final static int REQUEST_TIMEOUT = 2500;
    private final static int REQUEST_RETRIES = 3;
    private final static String SERVER_ENDPOINT = "tcp://localhost:5555";
    private final String message;
    private final ClientServiceProcesser clientService;

    public ClientService(Message message) {
        this.clientService = new ClientServiceProcesser(message);
        this.message = message.messageToJson();
    }

    public void processService() {
        try (ZContext ctx = new ZContext()) {
            System.out.println("Connecting to broker");
            ZMQ.Socket client = ctx.createSocket(SocketType.REQ);
            assert (client != null);
            client.connect(SERVER_ENDPOINT);

            ZMQ.Poller poller = ctx.createPoller(1);
            poller.register(client, ZMQ.Poller.POLLIN);

            int retriesLeft = REQUEST_RETRIES;
            client.send(message);

            while (retriesLeft-- != 0) {
                int rc = poller.poll(REQUEST_TIMEOUT);
                if (rc == -1) { break; }

                if (poller.pollin(0)) {
                    processReply(client);
                    break;
                } else {
                    System.out.println("No response from server, retrying\n");
                    poller.unregister(client);
                    ctx.destroySocket(client);
                    System.out.println("Reconnecting to broker");
                    client = ctx.createSocket(SocketType.REQ);
                    client.connect(SERVER_ENDPOINT);
                    poller.register(client, ZMQ.Poller.POLLIN);
                    client.send(message);
                }
            }

            if (retriesLeft == 0) {
                System.out.println("Server seems to be offline, aborting\n");
            } else {
                System.out.println("Operation terminated\n");
            }
            System.exit(1);
        }
    }

    private void processReply(ZMQ.Socket client) {
        String jsonMessage = client.recvStr();
        if (jsonMessage == null) { return; }
        Message reply = Message.jsonToRequest(jsonMessage);
        switch (reply.getType()) {
            case SUBSCRIBE_RESPONSE -> clientService.subscribeResponseMessageProcess((SubscribeResponseMessage) reply);
            case UNSUBSCRIBE_RESPONSE -> clientService.unsubscribeResponseMessageProcess((UnsubscribeResponseMessage) reply);
            case PUT_RESPONSE -> clientService.putResponseMessageProcess((PutResponseMessage) reply);
            case GET_RESPONSE -> clientService.getResponseMessageProcess((GetResponseMessage) reply);
            default -> System.out.println("Processing messages of type " + reply.getType() + "is not implemented yet");
        }
    }
}
