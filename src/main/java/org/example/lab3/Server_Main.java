package org.example.lab3;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.Scanner;

public class Server_Main {

    public static Registry registry;
    private static Random random = new Random();

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

            // Start the algorithm for each process
            for (final String rmi_name : registry.list()) {
                new Thread()
                {
                    public void run() {
                        try {
                            ParticipantProcess_Interface stub = (ParticipantProcess_Interface) registry.lookup(rmi_name);
                            stub.start();
                        } catch (RemoteException | NotBoundException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
