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

    public static List<Component> components = new ArrayList<>();
    public static int COMPONENT_START_PORT = 2000;

    public static void main(String args[]) throws IOException {

        try {
            //create and install a security manager
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new RMISecurityManager());
            }

            Registry registry = LocateRegistry.createRegistry(1099);

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

                Component component = new Component(i_am_host ? my_ip : other_ip, id, rmi_name_me, rmi_name_d_neighbor);
                RMI_Interface2 stub = (RMI_Interface2) UnicastRemoteObject.exportObject(component, 0);
                registry.rebind(rmi_name_me, stub);
                components.add(component);
            }

            for (Component c : components) {
                c.send_tid();
            }


            Thread.sleep(10000);
            for (Component c : components) {
                c.update_tid_and_check_condition();
                System.out.println(c.is_active());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
