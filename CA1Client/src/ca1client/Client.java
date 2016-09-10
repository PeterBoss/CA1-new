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

    public Client(String ip, int port) throws java.net.UnknownHostException {
        this.port = port;
        serverAddress = InetAddress.getByName(ip);
    }

    
    
    public void connect() throws IOException {
        socket = new Socket(serverAddress, port);
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);
    }

    public String interpretOutgoing(String raw) {
        String[] pieces = raw.split(":");
        if (pieces.length > 1) {
            if (!pieces[0].equals("LOGIN")) {
                return "MSG:" + pieces[0] + ":" + pieces[1];
            } else {
                return raw;
            }
        } else {
            return "MSG::" + raw;
        }

    }

    public void send(String msg) {
        System.out.println("Sending " + msg);
        output.println(interpretOutgoing(msg));
    }

    public void stop() throws IOException {
        output.println("LOGOUT:");
        input.close();
        output.close();
        socket.close();
    }

    public String recieve() {
        String msg = input.nextLine();
        System.out.println("Received: " + msg);
        return msg;
    }

}
