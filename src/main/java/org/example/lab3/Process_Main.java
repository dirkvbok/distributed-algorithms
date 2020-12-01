package org.example.lab3;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Process_Main {

    public static void main(String args[]) throws IOException {
        System.out.println("New process opened.");

        Registry registry = LocateRegistry.getRegistry(1099);
        String rmi_name = "rmi://localhost/process-" + args[0];
        Process process = new Process(rmi_name);
        Process_Interface stub = (Process_Interface) UnicastRemoteObject.exportObject(process, 0);
        registry.rebind(rmi_name, stub);
    }

}
