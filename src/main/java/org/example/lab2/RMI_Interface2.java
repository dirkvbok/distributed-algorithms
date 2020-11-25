package org.example.lab2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMI_Interface2 extends Remote {

  int retrieve_tid() throws RemoteException;

  int retrieve_ntid() throws RemoteException;

  int retrieve_nntid() throws RemoteException;

  boolean is_active() throws  RemoteException;

  void update_tid(int tid) throws RemoteException;

  void update_ntid(int ntid) throws RemoteException;

  void update_nntid(int ntid) throws RemoteException;

  boolean check_condition() throws RemoteException;

}
