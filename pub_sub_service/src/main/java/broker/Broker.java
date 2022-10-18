package broker;

import java.util.Random;

import org.zeromq.SocketType;
import org.zeromq.ZMQ.Socket;
import org.zeromq.ZContext;

public class Broker
{
    public static void main(String[] argv) throws Exception
    {
        Random rand = new Random(System.nanoTime());

        try (ZContext context = new ZContext()) {
            Socket server = context.createSocket(SocketType.REP);
            server.bind("tcp://*:5555");

            int cycles = 0;
            while (true) {
                String request = server.recvStr();
                cycles++;

                if (cycles > 3 && rand.nextInt(3) == 0) {
                    System.out.println("Simulating a crash");
                    break;
                }
                else if (cycles > 3 && rand.nextInt(3) == 0) {
                    System.out.println("Simulating CPU overload");
                    Thread.sleep(2000);
                }
                System.out.printf("Normal request (%s)\n", request);
                Thread.sleep(1000);
                server.send("RECEIVED");
            }
        }
    }
}
