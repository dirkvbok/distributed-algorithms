package org.example;

import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;

public class RMI_Main {

    public static void main(String args[]) {

        try {
            // Create and install a security manager
            if (System.getSecurityManager() == null) {System.setSecurityManager(new RMISecurityManager());}

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(1099);

            ArrayList<RMI_Process> processes = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                RMI_Process process = new RMI_Process(i);
                RMI_Interface stub = (RMI_Interface) UnicastRemoteObject.exportObject(process, 0);
                registry.bind("rmi://localhost:1099/process-" + i, stub);
                processes.add(process);
            }

            try {
                // Program should:
                //  - record for any two broadcasts whether one happens before the other
                //  - record all receive events in all processes
                //  - check the causal ordering

                // Test case 1
//                broadcast("m1", 0, processes);
//                Thread.sleep(2000);
//                broadcast("m2", 0, processes);

                // Test case 2
//                broadcast("m1", 0, processes);
//                Thread.sleep(2000);
//                broadcast("m2", 1, processes);
//                Thread.sleep(2000);
//                broadcast("m3", 0, processes);

                // Test case 3
                broadcast("m1", 0, processes);
                Thread.sleep(2000);
                broadcast("m2", 1, processes);
                Thread.sleep(2000);
                broadcast("m3", 2, processes);
                Thread.sleep(2000);
                broadcast("m4", 1, processes);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    private static void broadcast(String m, int index, ArrayList<RMI_Process> processes) throws InterruptedException, RemoteException, NotBoundException {
        processes.get(index).broadcast(m);
    }

}


