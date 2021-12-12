import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTCP {
    private static BufferedReader inFromUser;
    private static BufferedReader inFromServer;
    private static Socket socket;
    private static PrintWriter out;
    private static String message;
    private static String response;

    public static void main(String argv[]) throws Exception {
        inFromUser = new BufferedReader(new InputStreamReader(System.in));
        connect();
        out = new PrintWriter(socket.getOutputStream(), true);
        inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while (!socket.isClosed()) {
            sendMessage();
        }
    }
    public static void sendMessage() throws IOException {
        message = inFromUser.readLine();
        int outSize = message.getBytes().length;
        int responseSize;
        if (message.toLowerCase().equals("kill")) {
            socket.close();
            System.out.println("Connection close\nBye bye!");
        } else {
            out.println(message);
            if ((response = inFromServer.readLine()) != null) {
                responseSize = response.getBytes().length;
                System.out.println(response);
                System.out.println("message size = " + outSize + " response size = " + responseSize);
            }
        }
    }

    public static void connect() throws IOException {
        String ip = null;
        int port = -1;
        while (socket == null) {
            System.out.println("Provide server IP:");
            ip = inFromUser.readLine();
            System.out.println("Provide server port:");
            port = Integer.parseInt(inFromUser.readLine());
            socket = new Socket(ip, port);
        }
        System.out.println("Successfully connected with " + ip + " on port " + port);
        System.out.println("Type 'kill' to close connection");
    }
}


