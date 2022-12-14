package broker;

import messages.Message;
import org.zeromq.SocketType;
import org.zeromq.ZMQ.Socket;
import org.zeromq.ZContext;

import java.io.IOException;

public class Broker
{
    public static void main(String[] argv)
    {
        try (ZContext context = new ZContext()) {
            Socket server = context.createSocket(SocketType.REP);
            server.bind("tcp://*:5555");
            BrokerService brokerService = new BrokerService(server);
            while (true) {
                String jsonMessage = server.recvStr();
                Message message = Message.jsonToRequest(jsonMessage);
                brokerService.setMessage(message);
                brokerService.processMessage();
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
