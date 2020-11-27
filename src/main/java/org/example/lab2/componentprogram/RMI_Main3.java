package org.example.lab2.componentprogram;


import org.example.lab1.RMI_Interface;

import java.io.IOException;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RMI_Main3 {

    // My IP
    public static String my_ip = "192.168.2.25";    // Dirk
//    public static String my_ip = "192.168.1.100";   // Naqib

    // Other IP
//    public static String other_ip = "192.168.2.25";    // Dirk
    public static String other_ip = "192.168.1.100";   // Naqib

    public static boolean i_am_host = true;
    public static Registry registry;
    public static List<Component> components = new ArrayList<>();
    public static List<Component> components_to_remove = new ArrayList<>();
    public static int COMPONENT_START_PORT = 2000;

    public static void main(String args[]) throws IOException {

        try {
            //create and install a security manager
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new RMISecurityManager());
            }

            if (i_am_host) {
                registry = LocateRegistry.createRegistry(1099);
            } else {
                registry = LocateRegistry.getRegistry(other_ip, 1099);
            }

            int n = Integer.parseInt(args[0]);
            for (int i = 1; i < n+1; i++) {
                System.out.println(args[i]);
                int id = Integer.parseInt(args[i]);
                int port_component = COMPONENT_START_PORT+i;
                String rmi_name_d_neighbor = "rmi://" + my_ip + ":" + (port_component + 1);
                String rmi_name_me = "rmi://" + my_ip + ":" + port_component;

                // last component has link to other ip component
                if (i == n) {
                    // TODO: change my_ip to other_ip
                    rmi_name_d_neighbor = "rmi://" + my_ip + ":" + (COMPONENT_START_PORT + 1);
                }

                // Create components with link to host, its initial tid, its rmi name on the registry, and rmi name of neighbor
                Component component = new Component(i_am_host ? my_ip : other_ip, id, rmi_name_me, rmi_name_d_neighbor);
                RMI_Interface2 stub = (RMI_Interface2) UnicastRemoteObject.exportObject(component, 0);
                registry.rebind(rmi_name_me, stub);

                // TODO: retrieve components from other ip
                components.add(component);
            }

            // Rounds loop
            int round_count = 1;
            while(components.size() > 1) {

                // one round
                System.out.println("Round " + round_count);
                for (Component c : components) {
                    // send_tid starts the election for every component
                    c.send_tid();
                }

                // Wait until every process has received (n)ntid's
                Thread.sleep(5000);

                // end round
                System.out.println("tid's:");
                for (Component c : components) {
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
            RMI_Interface2 stub = (RMI_Interface2) registry.lookup(components.get(0).rmi_name_me);
            System.out.println("winner: " + stub.get_tid());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
