package org.example;

import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * The remote algorithm class implements the remote interface,
 * in which the actual work of a single process of the distributed algorithm is performed.
 */
public class RMI_Process implements RMI_Interface {

    private Random random = new Random();
    private int[] V = new int[3];
    private int index;
    private List<Message> buffer = new ArrayList<>();

    public RMI_Process(int index) {
        this.index = index;
    }

    @Override
    public void broadcast(String m) throws InterruptedException, RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(1099);
        V[index]++;

        for (int i = 0; i < 3; i++) {
            if (i != index) {
                // Random message delay between 0 - 1 seconds
                Thread.sleep(random.nextInt(1000));
                RMI_Interface stub = (RMI_Interface) registry.lookup("rmi://localhost:1099/process-" + i);
                Message message = new Message(m, V, index);
                System.out.println("Broadcasting message " + message.toString() + "at process " + index);
                stub.receive(message);
            }
        }
    }

    @Override
    public void receive(Message message) throws RemoteException, InterruptedException {
        System.out.println("Receive message " + message.toString() + " at process " + index);
        if (deliveryCondition(message)) {
            deliver(message);

            // While there are deliverable messages in buffer:
            while(nextDeliverableMessage() != null) {
                deliver(Objects.requireNonNull(nextDeliverableMessage()));
            }
        } else {
            buffer.add(message);
            System.out.println("Buffer add message " + message.toString() + " at process " + index);
        }
    }

    @Override
    public void deliver(Message message) throws RemoteException, InterruptedException {
        V[message.senderIndex]++;
        System.out.println("Deliver message [" + message.toString()+ "] at process " + index);
        buffer.remove(message);
    }

    /**
     * Get next deliverable message, null if none.
     * @return next deliverable message
     */
    private Message nextDeliverableMessage() {
        for (Message m : buffer) {
            if (deliveryCondition(m)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Check if message can be delivered.
     * @param message message to be delivered
     * @return true if message can be delivered
     */
    private boolean deliveryCondition(Message message) {
        int[] myV = V.clone();
        myV[message.senderIndex]++;

        for (int i = 0; i < message.V.length; i++) {
            if (myV[i] < message.V[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * String representation of vector clock [i, j, k].
     * @param vectorClock vector clock array
     * @return string representation of vector clock
     */
    private String vectorClockToString(int[] vectorClock) {
        String result = "[";
        for (int i = 0; i < vectorClock.length; i++) {
            if (i != vectorClock.length - 1) {
                result += vectorClock[i] + ", ";
            } else {
                result += vectorClock[i] + "]";
            }
        }
        return result;
    }

}
