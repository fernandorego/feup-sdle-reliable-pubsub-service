package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ErrorUtils {
    public static void usageError(String error) {
        Logger usage = LogManager.getLogger("Client");
        usage.error(error);
        System.out.println("Put operation usage:");
        System.out.println("bash client.sh put <topic> <path_to_file>\n");

        System.out.println("Get operation usage:");
        System.out.println("bash client.sh get <topic> <client_id>\n");

        System.out.println("Subscribe operation usage:");
        System.out.println("bash client.sh subscribe <topic> <client_id>\n");

        System.out.println("Unsubscribe operation usage:");
        System.out.println("bash client.sh unsubscribe <topic> <client_id>\n");

        System.exit(1);
    }
}
