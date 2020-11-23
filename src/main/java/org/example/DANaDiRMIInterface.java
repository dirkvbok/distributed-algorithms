package org.example;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The remote interface defines the methods that can be called remotely (see Section 1).
 */
public interface DANaDiRMIInterface extends Remote {


    String sayHello() throws RemoteException;

    int registerClient() throws RemoteException;

    String broadcastMessage(String m, int[] V) throws RemoteException;

    String receiveMessage(String m, int[] V) throws RemoteException;

    String deliverMessage(String m) throws RemoteException;
}
