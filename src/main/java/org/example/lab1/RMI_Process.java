package org.example.lab1;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
    public void broadcast(String m) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(1099);
        V[index]++;

        for (int i = 0; i < 3; i++) {
            if (i != index) {
                RMI_Interface stub = (RMI_Interface) registry.lookup("rmi://localhost:1099/process-" + i);
                Message message = new Message(m, V.clone(), index);
                String timeStamp = new SimpleDateFormat("HH.mm.ss.SSS").format(new Timestamp(System.currentTimeMillis()));
                System.out.println("[" + timeStamp + "] [p" + index + "] Broadcasting to [p" + i + "] " + message.toString());
                send(message, stub);
            }
        }
    }

    private void send(final Message message, final RMI_Interface stub) {
        new Thread()
        {
            public void run() {
                // Random message delay between 0 - 10 seconds
                try {
                    Thread.sleep(random.nextInt(10000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    stub.receive(message);
                } catch (RemoteException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void receive(Message message) throws RemoteException, InterruptedException {
        String timeStamp = new SimpleDateFormat("HH.mm.ss.SSS").format(new Timestamp(System.currentTimeMillis()));
        System.out.println("[" + timeStamp + "] [p" + index + "] Receive " + message.toString() + " my V: " + vectorClockToString(V));
        if (deliveryCondition(message)) {
            deliver(message);

            // While there are deliverable messages in buffer:
            while(nextDeliverableMessage() != null) {
                deliver(Objects.requireNonNull(nextDeliverableMessage()));
            }
        } else {
            buffer.add(message);
            timeStamp = new SimpleDateFormat("HH.mm.ss.SSS").format(new Timestamp(System.currentTimeMillis()));
            System.out.println("[" + timeStamp + "] [p" + index + "] Buffer add " + message.toString());
        }
    }

    @Override
    public void deliver(Message message) {
        V[message.senderIndex]++;
        buffer.remove(message);
        String timeStamp = new SimpleDateFormat("HH.mm.ss.SSS").format(new Timestamp(System.currentTimeMillis()));
        System.out.println("[" + timeStamp + "] [p" + index + "] Deliver " + message.toString() + " my V: " + vectorClockToString(V));
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
     * String representation of vector clock [i, j, k].
     * @param vectorClock vector clock array
     * @return string representation of vector clock
     */
    private String vectorClockToString(int[] vectorClock) {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < vectorClock.length; i++) {
            if (i != vectorClock.length - 1) {
                result.append(vectorClock[i]).append(", ");
            } else {
                result.append(vectorClock[i]).append("]");
            }
        }
        return result.toString();
    }

}
