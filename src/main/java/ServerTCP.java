import java.io.*;
import java.net.*;

public class ServerTCP {
    public static String clientSentence;
    public static ServerSocket welcomeSocket;
    public static Socket connectionSocket;
    public static PrintWriter out;
    public static BufferedReader inFromClient;

    public static void main(String argv[]) throws Exception {
        welcomeSocket = new ServerSocket(7);
        System.out.println("Server started on port: " + welcomeSocket.getLocalPort());
        while (true) {
            createConnection();
            echoClientMessage();
        }
    }
    public static void createConnection() throws IOException {
        connectionSocket = welcomeSocket.accept();
        System.out.println("Client connected");
        inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        out = new PrintWriter(connectionSocket.getOutputStream(), true);
    }

    public static void echoClientMessage() throws IOException {
        while (!connectionSocket.isClosed()) {
            clientSentence = inFromClient.readLine();
            if (clientSentence == null) {
                connectionSocket.close();
                System.out.println("Client disconnected");
            } else {
                System.out.println("Received: " + clientSentence);
                out.println(clientSentence);
            }
        }
    }
}
