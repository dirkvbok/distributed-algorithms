package org.example.lab3;

import java.io.IOException;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Server_Main {

    public static Registry registry;

    public static void main(String args[]) throws IOException {

        try {
            //create and install a security manager
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new RMISecurityManager());
            }

            registry = LocateRegistry.createRegistry(1099);

            // Wait for user to start consensus algorithm
            boolean start = false;
            Scanner userInput = new Scanner(System.in);
            System.out.println("Press 'y' to start.");

            while(!start) {
                String input = userInput.nextLine();

                if (!input.isEmpty()) {
                    start = true;
                    userInput.close();
                }
            }

            Process_Interface stub = (Process_Interface) registry.lookup(registry.list()[0]);
            stub.broadcast(new Message(MessageType.NOTIFICATION, 1, true));


        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
