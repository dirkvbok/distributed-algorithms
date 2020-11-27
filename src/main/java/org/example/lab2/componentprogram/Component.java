package org.example.lab2.componentprogram;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Component implements RMI_Interface {
  private int tid;
  private int ntid;
  private int nntid;
  private boolean active;
  private final String rmi_name_d_neighbor;
  public final String rmi_name_me;
  private final Registry registry;

  public Component(String host_ip, int tid, String rmi_name_me, String rmi_name_d_neighbor) throws RemoteException {
    this.tid = tid;
    this.active = true;
    this.rmi_name_d_neighbor = rmi_name_d_neighbor;
    this.rmi_name_me = rmi_name_me;

    this.registry = LocateRegistry.getRegistry(host_ip, 1099);
  }

  @Override
  public void send_tid() throws RemoteException, NotBoundException {
    if (active) {
      RMI_Interface stub = (RMI_Interface) registry.lookup(rmi_name_d_neighbor);
      stub.retrieve_ntid(tid);
    }
  }

  @Override public void retrieve_ntid(int ntid) throws RemoteException, NotBoundException {
    RMI_Interface stub = (RMI_Interface) registry.lookup(rmi_name_d_neighbor);

    if (active) {
      this.ntid = ntid;
      stub.retrieve_nntid(Math.max(tid, ntid));
    } else {
      stub.retrieve_ntid(ntid);
    }
  }

  @Override public void retrieve_nntid(int nntid) throws RemoteException, NotBoundException {
    RMI_Interface stub = (RMI_Interface) registry.lookup(rmi_name_d_neighbor);

    if (active) {
      this.nntid = nntid;
    } else {
      stub.retrieve_nntid(nntid);
    }
  }

  @Override public boolean check_condition() throws RemoteException {
    System.out.println("[" + rmi_name_me + "] check_condition: ntid:" + ntid + " >= tid:" + tid + " && ntid:" + ntid + " >= nntid:" + nntid);
     if(ntid >= tid && ntid >= nntid){
       //update the tid
       tid = ntid;
     } else {
       //update activeness
       active = false;
     }

     //return status
     return active;
  }

  @Override
  public int get_tid() throws RemoteException {
    return tid;
  }

  @Override
  public String get_rmi_name() throws RemoteException {
    return rmi_name_me;
  }

}
