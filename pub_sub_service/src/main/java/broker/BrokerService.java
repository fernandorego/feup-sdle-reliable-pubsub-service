package broker;

import messages.*;
import org.zeromq.ZMQ.Socket;

import java.util.ArrayList;


public class BrokerService {
    private Message message;
    private final Socket server;
    private final BrokerServiceProcesser brokerServiceProcesser;

    public BrokerService(Socket server) {
        this.message = null;
        this.server = server;
        this.brokerServiceProcesser = new BrokerServiceProcesser(new ArrayList<>());
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void processMessage() {
        Message reply_message;
        if (this.message == null) { return; }
        reply_message = switch (this.message.getType()) {
            case SUBSCRIBE -> brokerServiceProcesser.subscribeMessageProcess((SubscribeMessage) this.message);
            case UNSUBSCRIBE -> brokerServiceProcesser.unsubscribeMessageProcess((UnsubscribeMessage) this.message);
            case PUT -> brokerServiceProcesser.putMessageProcess((PutMessage) this.message);
            case GET -> brokerServiceProcesser.getMessageProcess((GetMessage) this.message);
            default -> null;
        };
        if (reply_message != null) {
            server.send(reply_message.messageToJson());
        } else {
            System.out.println("Processing messages of type " + this.message.getType() + "is not implemented yet");
        }
    }
}
