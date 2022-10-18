package client;

import messages.Message;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class ClientService {
    private final static int REQUEST_TIMEOUT = 2500;
    private final static int REQUEST_RETRIES = 3;
    private final static String SERVER_ENDPOINT = "tcp://localhost:5555";
    private final String message;

    public ClientService(Message message) {
        this.message = message.messageToJson();
    }

    public void sendMessage() {
        try (ZContext ctx = new ZContext()) {
            System.out.println("Connecting to broker");
            ZMQ.Socket client = ctx.createSocket(SocketType.REQ);
            assert (client != null);
            client.connect(SERVER_ENDPOINT);

            ZMQ.Poller poller = ctx.createPoller(1);
            poller.register(client, ZMQ.Poller.POLLIN);

            int retriesLeft = REQUEST_RETRIES;
            client.send(message);

            int expect_reply = 1;
            while (expect_reply > 0) {
                int rc = poller.poll(REQUEST_TIMEOUT);
                if (rc == -1) { break; }

                if (poller.pollin(0)) {
                    String reply = client.recvStr();
                    if (reply == null) { break; }
                    //TODO: parse server reply and do actions according to Message type
                    System.out.printf("Server replied %s\n", reply);
                    retriesLeft = REQUEST_RETRIES;
                    expect_reply = 0;
                } else if (--retriesLeft == 0) {
                    System.out.println("Server seems to be offline, aborting\n");
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
        }
    }
}
