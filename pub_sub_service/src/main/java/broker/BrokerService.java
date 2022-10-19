package broker;

import messages.Message;
import messages.SubscribeMessage;
import org.zeromq.ZMQ.Socket;

import java.util.ArrayList;


public class BrokerService {
    private Message message;
    private final Socket server;
    private final ServiceProcesser serviceProcesser;

    public BrokerService(Socket server) {
        this.message = null;
        this.server = server;
        this.serviceProcesser = new ServiceProcesser(new ArrayList<>());
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void processMessage() {
        Message reply_message = null;
        if (this.message == null) { return; }
        switch (this.message.getType()) {
            case SUBSCRIBE -> {
                reply_message = serviceProcesser.subscribeMessageProcess((SubscribeMessage) this.message);
            }
            default -> System.out.println("Processing messages of type " + this.message.getType() + "is not implemented yet");
        }
        if (reply_message != null) {
            server.send(reply_message.messageToJson());
        }
    }


}
