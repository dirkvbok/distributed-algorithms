package org.example.lab2.componentprogram;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMI_Server_Interface extends Remote {

    void register_component(String rmi_name) throws RemoteException, NotBoundException;
}
