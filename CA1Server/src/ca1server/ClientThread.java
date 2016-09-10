package ca1server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peter
 */
public class ClientThread extends Thread implements Observer {

    private ChatServer server;
    private Socket socket;
    private PrintWriter writer;
    private Scanner reader;
    private String username;

    public ClientThread(ChatServer server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        writer = new PrintWriter(socket.getOutputStream(), true);
        reader = new Scanner(socket.getInputStream());
    }

    @Override
    public void run() {
        boolean isValid = true;
        try {
            while (isValid) { //turns out doing while (true) is bad.
                String message = reader.nextLine();
                System.out.println("Received: " + message);
                String[] pieces = message.split(":");
                switch (pieces[0]) {
                    case "LOGOUT": {
                        isValid = false;
                        break; //turns out break is for breaking out of the switch NOT the loop we're in
                    }
                    case "LOGIN": {

                        if (pieces.length == 2) { //checking if true to protocol
                            login(pieces[1]);
                            break;
                        } else {
                            isValid = false;
                            break;
                        }
                    }
                    case "MSG": {
                        if (pieces.length == 3) { //checking if true to protocol

                            String receivers = pieces[1];
                            String msg = pieces[2];

                            if (receivers.equals("")) { //if MSG::<text>
                                server.sendToAll(msg, username);
                                break;
                            } else { //for sending to specific user/users
                                server.send(receivers, msg, username);
                                break;
                            }
                        } else { 
                            isValid = false;
                            break;
                        }
                    }
                    default: { //ya'll better be followin' protocol
                        isValid = false;
                        break;
                    }
                }
            }
        } finally {
            logout(); //gets its own method, just to make the code easier to look at
        }
    }

    private void login(String username) {
        this.username = username;
        server.addClient(this); //adds this thread to the Map with username as key
        server.addObserver(this);
        server.sendUsernames();
    }

    private void logout() {
        try {
            writer.println("LOGGING OUT");
            server.deleteObserver(this);
            server.removeClient(this); //removes from the Map
            socket.close();
            System.out.println("Closed a connection");
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getUsername() { //used when we put in Map
        return username;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Sending: " + arg);
        writer.println(arg);
    }
}
