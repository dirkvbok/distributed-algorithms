package org.example.lab3;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ParticipantProcess_Interface extends Remote {

    void start() throws RemoteException, NotBoundException, InterruptedException;

    void broadcast(Message m) throws RemoteException, NotBoundException;

    void receive(Message m) throws RemoteException, NotBoundException;;

}
