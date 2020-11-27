package org.example.lab2.componentprogram;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMI_Interface2 extends Remote {

  void send_tid() throws RemoteException, NotBoundException;

  void retrieve_ntid(int ntid) throws RemoteException, NotBoundException;

  void retrieve_nntid(int nntid) throws RemoteException;

  void update_tid_and_check_condition() throws RemoteException;

  boolean is_active() throws  RemoteException;

  boolean check_condition() throws RemoteException;

}
