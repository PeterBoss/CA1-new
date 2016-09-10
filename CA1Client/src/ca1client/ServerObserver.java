package ca1client;

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

    public void stop() {
        keepRunning = false;
    }
    
    @Override
    public void run() {

        while (keepRunning) {
            try {
                String line = client.recieve();
                setChanged();
                notifyObservers(line);
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerObserver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
