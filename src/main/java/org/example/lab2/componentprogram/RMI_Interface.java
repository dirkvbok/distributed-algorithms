package org.example.lab2.componentprogram;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMI_Interface extends Remote {

  int get_tid() throws RemoteException;

  String get_rmi_name() throws RemoteException;

  void send_tid() throws RemoteException, NotBoundException;

  void receive_ntid(int ntid) throws RemoteException, NotBoundException;

  void receive_nntid(int nntid) throws RemoteException, NotBoundException;

  boolean check_condition() throws RemoteException;

}
