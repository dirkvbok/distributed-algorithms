package org.example.lab2.componentprogram.testcase1;

import org.example.lab2.componentprogram.Component;
import org.example.lab2.componentprogram.RMI_Interface;

import java.io.IOException;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMI_Client2_Main {

    // My IP
    public static String my_ip = "192.168.2.25";    // Dirk
//    public static String my_ip = "192.168.1.100";   // Naqib

    public static Registry registry;
    public static int MY_PORT = 3000;
    public static int OTHER_PORT = 2000;

    public static void main(String args[]) throws IOException {

        try {
            //create and install a security manager
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new RMISecurityManager());
            }

            registry = LocateRegistry.getRegistry(my_ip, 1099);

            int n = Integer.parseInt(args[0]);
            System.out.println("Election participants:");
            for (int i = 1; i < n+1; i++) {
                System.out.println(args[i]);
                int id = Integer.parseInt(args[i]);

                String rmi_name_d_neighbor = "rmi://" + my_ip + ":" + MY_PORT + "/component-" + (i+1);
                String rmi_name_me = "rmi://" + my_ip + ":" + MY_PORT + "/component-" + i;

                // last component has link to other machines first component
                if (i == n) {
                    rmi_name_d_neighbor = "rmi://" + my_ip + ":" + OTHER_PORT + "/component-" + 1;
                }

                // Create components with link to host, its initial tid, its rmi name on the registry, and rmi name of neighbor
                Component component = new Component(my_ip , id, rmi_name_me, rmi_name_d_neighbor);
                registry = LocateRegistry.getRegistry(my_ip, 1099);
                RMI_Interface stub = (RMI_Interface) UnicastRemoteObject.exportObject(component, 0);
                registry.rebind(rmi_name_me, stub);
            }
            System.out.println();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
