package org.example.lab2.componentprogram;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

class Component implements RMI_Interface2 {
  private int tid;
  private int ntid;
  private int nntid;
  private boolean active;
  private String rmi_name_d_neighbor;
  private String rmi_name_me;
  private Registry registry;

  public Component(String host_ip, int tid, String rmi_name_me, String rmi_name_d_neighbor) throws RemoteException {
    this.tid = tid;
    this.active = true;
    this.rmi_name_d_neighbor = rmi_name_d_neighbor;
    this.rmi_name_me = rmi_name_me;

    this.registry = LocateRegistry.getRegistry(host_ip, 1099);
  }

  @Override
  public void send_tid() throws RemoteException, NotBoundException {
    System.out.println("[" + rmi_name_me + "] sends tid to [" + rmi_name_d_neighbor + "]");
    RMI_Interface2 stub = (RMI_Interface2) registry.lookup(rmi_name_d_neighbor);
    stub.retrieve_ntid(tid);
  }

  @Override public void retrieve_ntid(int ntid) throws RemoteException, NotBoundException {
    System.out.println("[" + rmi_name_me + "] retrieves ntid");
    RMI_Interface2 stub = (RMI_Interface2) registry.lookup(rmi_name_d_neighbor);

    if (active) {
      this.ntid = ntid;
      stub.retrieve_nntid(Math.max(tid, ntid));
    } else {
      stub.retrieve_nntid(ntid);
    }
  }

  @Override public void retrieve_nntid(int nntid) throws RemoteException {
    System.out.println("[" + rmi_name_me + "] retrieves nntid");
    this.nntid = nntid;

    boolean condition = this.check_condition();
  }

  @Override
  public void update_tid_and_check_condition() throws RemoteException {
    boolean condition = this.check_condition();
  }

  @Override public boolean is_active() throws RemoteException {
    return this.active;
  }

  @Override public boolean check_condition() throws RemoteException {
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


}
