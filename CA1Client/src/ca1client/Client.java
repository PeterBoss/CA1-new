package ca1client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author Peter
 */
public class Client {

    private Socket socket;
    private InetAddress serverAddress;
    private int port;
    private Scanner input;
    private PrintWriter output;

    public void connect(String ip, int port) throws UnknownHostException, IOException {
        this.port = port;
        serverAddress = InetAddress.getByName(ip);
        socket = new Socket(serverAddress, port);
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);
    }

    public boolean isClosed() {
        return socket.isClosed();
    }

    public void send(String msg) {
        System.out.println("Sending " + msg);
        output.println(msg);
    }

    public void stop() throws IOException {
        output.println("LOGOUT:");
    }

    public String recieve() {
        
        String msg = input.nextLine();
        System.out.println("Received: " + msg);
        return msg;
    }

}
