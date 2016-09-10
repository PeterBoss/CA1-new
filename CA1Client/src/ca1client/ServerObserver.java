package ca1client;

import java.io.IOException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peter
 */
public class ServerObserver extends Observable implements Runnable {

    Client client;
    boolean keepRunning = true;

    public ServerObserver(Client client) {
        this.client = client;
    }

    public void stop() throws IOException {
        keepRunning = false;
        client.stop();
    }

    public String interpret(String raw) {
        String[] pieces = raw.split(":");

        switch (pieces[0]) {
            case "CLIENTLIST": {
                return "Users online: " + pieces[1];
            }
            case "MSGRES": {
                return pieces[1] + ": " + pieces[2];
            }
            default:
                return raw;
        }

    }

    @Override
    public void run() {
        System.out.println("Connecting");
        try {
            client.connect("localhost", 8080); //temp hardcode
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (keepRunning) {
            try {
                String line = client.recieve();
                line = interpret(line);
                setChanged();
                notifyObservers(line);
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerObserver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
