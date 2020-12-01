package org.example.lab3;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Process_Interface extends Remote {

    void broadcast(Message m) throws RemoteException, NotBoundException;

    void receive(Message m) throws RemoteException, NotBoundException;;

}
