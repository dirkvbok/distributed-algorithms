package org.example.lab2.componentprogram;

import java.io.IOException;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RMI_Server_Main {

    public static Registry registry;
    public static List<RMI_Interface> components = new ArrayList<>();
    public static List<RMI_Interface> components_to_remove = new ArrayList<>();

    public static void main(String args[]) throws IOException {

        try {
            //create and install a security manager
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new RMISecurityManager());
            }

            registry = LocateRegistry.createRegistry(1099);

            // Wait for user to start election
            boolean start_election = false;
            Scanner userInput = new Scanner(System.in);
            System.out.println("Press 'y' to start election.");

            while(!start_election) {
                String input = userInput.nextLine();

                if (!input.isEmpty()) {
                    start_election = true;
                    userInput.close();
                }
            }

            String[] rmi_list = registry.list();
            for(String rmi_name : rmi_list) {
                RMI_Interface stub = (RMI_Interface) registry.lookup(rmi_name);
                components.add(stub);
            }

            // Rounds loop
            int round_count = 1;
            while(components.size() > 1) {

                // one round
                System.out.println("Round " + round_count);
                for (RMI_Interface c : components) {
                    // send_tid starts the election for every component
                    c.send_tid();
                }

                // Wait until every process has received (n)ntid's
                Thread.sleep(5000);

                // end round
                System.out.println("tid's:");
                for (RMI_Interface c : components) {
                    System.out.println(c.get_tid());

                    boolean active = c.check_condition();
                    if (!active) {
                        components_to_remove.add(c);
                    }
                }

                // Keep track of which components are in the next round
                components.removeAll(components_to_remove);
                System.out.println();
                round_count++;
            }

            // Last remaining component is the winner
            RMI_Interface stub = (RMI_Interface) registry.lookup(components.get(0).get_rmi_name());
            System.out.println("winner: " + stub.get_tid());

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
