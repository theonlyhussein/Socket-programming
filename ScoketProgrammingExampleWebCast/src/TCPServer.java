import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.text.SimpleDateFormat;

public class TCPServer {

    public static void main(String[] args) throws Exception {
        // Create a server socket that listens on port 6789
        ServerSocket welcomeSocket = new ServerSocket(8080);

        while (true) {
            // Accept a connection from a client
            Socket connectionSocket = welcomeSocket.accept();
            
            // Create a new thread for each client connection
            new ClientHandler(connectionSocket).start();
        }
    }
}

class ClientHandler extends Thread {
    private Socket connectionSocket;

    public ClientHandler(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    public void run() {
        try {
            // Create input and output streams for the socket
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            String clientSentence;

            // Continuously listen for messages from the client
            while (true) {
                clientSentence = inFromClient.readLine();

                if (clientSentence == null || clientSentence.equalsIgnoreCase("EXIT")) {
                    
                	break;
                }

                String response = "";

                switch (clientSentence.toUpperCase()) {
                    case "DATE":
                        response = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                        break;
                    case "TIME":
                        response = new SimpleDateFormat("HH:mm:ss").format(new Date());
                        break;
                    default:
                        response = "Invalid command";
                        break;
                }

                // Send the response back to the client
                outToClient.writeBytes(response + '\n');
            }

            // Close the connection
            connectionSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

