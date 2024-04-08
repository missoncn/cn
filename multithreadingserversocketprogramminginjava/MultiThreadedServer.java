import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedServer {
    public static void main(String[] args) {
        final int PORT_NUMBER = 12345; // Port number on which the server listens

        try {
            ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
            System.out.println("Server started. Listening on port " + PORT_NUMBER);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Wait for a client to connect
                System.out.println("Client connected: " + clientSocket);

                // Create a new thread for each client connection
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private Socket clientSocket;
    private BufferedReader inputReader;
    private PrintWriter outputWriter;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outputWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String inputLine;
            while ((inputLine = inputReader.readLine()) != null) {
                System.out.println("Received from client: " + inputLine);
                outputWriter.println("Server received: " + inputLine); // Echo back to client
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
