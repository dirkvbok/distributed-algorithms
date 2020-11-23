package org.example;

import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

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

            processes.get(0).broadcast("hoi", new int[3]);

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

}
