import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MyClient {
    public static void main(String[] args) {
        final String SERVER_ADDRESS = "localhost"; // Server address
        final int PORT_NUMBER = 12345; // Server port number

        try {
            Socket socket = new Socket(SERVER_ADDRESS, PORT_NUMBER);
            System.out.println("Connected to server: " + socket);

            BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outputWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            String userInput;
            while ((userInput = consoleReader.readLine()) != null) {
                outputWriter.println(userInput); // Send user input to the server
                String serverResponse = inputReader.readLine(); // Receive response from server
                System.out.println("Server response: " + serverResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
