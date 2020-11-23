package org.example;

import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;

/**
 * The remote algorithm class implements the remote interface,
 * in which the actual work of a single process of the distributed algorithm is performed.
 */
public class RMI_Process implements RMI_Interface {

    private Random random = new Random();
    private int[] V = new int[3];
    private int index;

    public RMI_Process(int index) {
        this.index = index;
    }

    @Override
    public void broadcast(String m, int[] V) throws InterruptedException, RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(1099);
        System.out.println("Broadcasting message [" + m + "]");

//        Thread.sleep(random.nextInt(2000));
//        System.out.println(m);

        for (int i = 0; i < 3; i++) {
            if (i != index) {
                RMI_Interface stub = (RMI_Interface) registry.lookup("rmi://localhost:1099/process-" + i);
                stub.receive(m, V);
            }
        }

    }

    @Override
    public void receive(String m, int[] V) throws RemoteException, InterruptedException {
        System.out.println(toString(V));
    }

    @Override
    public void deliver(String m) throws RemoteException, InterruptedException {

    }

    private String toString(int[] vectorClock) {
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
