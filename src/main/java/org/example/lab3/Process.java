package org.example.lab3;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Process implements Process_Interface {

    private String rmi_my_name;

    public Process(String rmi_my_name) {
        this.rmi_my_name = rmi_my_name;
    }

    @Override
    public void broadcast(Message m) throws RemoteException, NotBoundException {
        System.out.println("broadcasting");
        Registry registry = LocateRegistry.getRegistry(1099);
        String[] rmi_list = registry.list();
        for(String rmi_name : rmi_list) {
            if (!rmi_name.equals(rmi_my_name)) {
                Process_Interface stub = (Process_Interface) registry.lookup(rmi_name);
                stub.receive(m);
            }
        }
    }

    @Override
    public void receive(Message m) {
        System.out.println("Receiving: " + m.w);
    }
}
