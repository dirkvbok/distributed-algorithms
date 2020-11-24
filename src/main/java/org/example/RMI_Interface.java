package org.example;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The remote interface defines the methods that can be called remotely (see Section 1).
 */
public interface RMI_Interface extends Remote {

    void broadcast(String m) throws RemoteException, InterruptedException, NotBoundException;

    void receive(Message message) throws RemoteException, InterruptedException;

    void deliver(Message message) throws RemoteException, InterruptedException;

}
