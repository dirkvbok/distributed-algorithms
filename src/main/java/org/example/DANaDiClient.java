package org.example;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class DANaDiClient {

    private DANaDiClient() {}

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            DANaDiRMIInterface stub = (DANaDiRMIInterface) registry.lookup("rmi://localhost:1099/remote-object");
            String response = stub.sayHello();
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
