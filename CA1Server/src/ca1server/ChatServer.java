package ca1server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peter
 */
public class ChatServer extends Observable {

    private static ServerSocket serverSocket;
    private static String ip;
    private static int port;
    private static boolean keepRunning;
    private ConcurrentMap<String, ClientThread> clientMap = new ConcurrentHashMap<>(); //for getting ClientThreads from their username

    public void stopServer() {
        keepRunning = false;
    }

    public void startServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
        keepRunning = true;
        System.out.println("Server started. Listening on: " + port + ", bound to: " + ip);

        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(ip, port));
            do {
                Socket socket = serverSocket.accept();
                System.out.println("Connected to a client");
                ClientThread clt = new ClientThread(this, socket);
                clt.start();
            } while (keepRunning);

        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addClient(ClientThread ch) {
        clientMap.put(ch.getUsername(), ch);
    }

    public void removeClient(ClientThread ch) {
        clientMap.remove(ch.getUsername());
    }

    public void sendUsernames() {
        StringBuilder str = new StringBuilder("CLIENTLIST:");
        
        Set<String> names = clientMap.keySet();
        
        for (String name : names) {
            str.append(name);
            str.append(",");
        }
        str.deleteCharAt(str.length()-1); //just taking that extra comma off
        setChanged();
        notifyObservers(str);
    }

    void sendToAll(String msg, String sender) {
        StringBuilder str = new StringBuilder("MSGRES:");
        str.append(sender);
        str.append(":");
        str.append(msg);
        setChanged();
        notifyObservers(str);
    }

    void send(String names, String msg, String sender) {
        StringBuilder str = new StringBuilder("MSGRES:");
        str.append(sender);
        str.append(":");
        str.append(msg);
        setChanged();

        String[] receivers = names.split(",");

        for (String name : receivers) {
            clientMap.get(name).update(this, str); //map being useful
        }
    }

    public static void main(String[] args) {

        new ChatServer().startServer(args[0], Integer.valueOf(args[1]));

    }

}
