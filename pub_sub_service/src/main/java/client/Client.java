package client;

import messages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ErrorUtils;

import java.io.IOException;

public class Client {

    public static void main(String[] argv)
    {
        String error_message = "Parsing client arguments error";
        if (argv.length != 3) {
            ErrorUtils.usageError(error_message);
            System.exit(1);
        }

        Message message = switch (argv[0]) {
            case "put" -> {
                try {
                    yield new PutMessage(argv[1], argv[2]);
                } catch (IOException e) {
                    error_message = "File does not exists";
                    yield null;
                }
            }
            case "get" -> new GetMessage(argv[1], argv[2]);
            case "subscribe" -> new SubscribeMessage(argv[1], argv[2]);
            case "unsubscribe" -> new UnsubscribeMessage(argv[1], argv[2]);
            default -> null;
        };

        if (message == null) {
            ErrorUtils.usageError(error_message);
            System.exit(1);
        }

        ClientService service = new ClientService(message);
        service.sendMessage();
    }
}
