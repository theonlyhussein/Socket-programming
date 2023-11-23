import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient {

	public static void main(String argv[]) throws Exception {
		String sentence;
		String modifiedSentence;

        // Create a BufferedReader to read input from the user (console)
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        // Create a socket for the client to connect to a server at localhost on port 6789
		Socket clientSocket = new Socket("10.128.0.2", 8080);
		System.out.println("Client successfully established TCP connection.\n"
				+ "Client(local) end of the connection uses port " 
				+ clientSocket.getLocalPort() 
				+ " and server(remote) end of the connection uses port "
				+ clientSocket.getPort());

        // Create a DataOutputStream for sending data to the server
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		
        // Create a BufferedReader to read data from the server
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        // Read a line of text from the user
		sentence = inFromUser.readLine().toUpperCase();
		
        // Continue reading input from the user until they type "exit"
		while (sentence.toLowerCase().compareTo("EXIT") != 0) {
			
			outToServer.writeBytes(sentence + '\n');

			modifiedSentence = inFromServer.readLine();

			System.out.println("FROM SERVER: " + modifiedSentence);
			sentence = inFromUser.readLine();
		}

		clientSocket.close();
	}
}