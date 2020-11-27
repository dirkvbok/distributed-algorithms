package org.example.lab2.componentprogram;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RMI_Server_Main implements RMI_Server_Interface {

    public static Registry registry;
    public static List<Component> components = new ArrayList<>();

    public static void main(String args[]) throws IOException {

        try {

            //create and install a security manager
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new RMISecurityManager());
            }

            registry = LocateRegistry.createRegistry(1099);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register_component(int id, String rmi_name_me) throws RemoteException, NotBoundException {

        Component component = new Component(i_am_host ? my_ip : other_ip, id, rmi_name_me, rmi_name_d_neighbor);
        RMI_Interface2 stub = (RMI_Interface2) UnicastRemoteObject.exportObject(component, 0);
        registry.rebind(rmi_name_me, stub);

        RMI_Interface2 stub = (RMI_Interface2) registry.lookup(rmi_name);
        components.add((Component) stub);
    }
}
