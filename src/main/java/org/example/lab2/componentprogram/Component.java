package org.example.lab2.componentprogram;

import java.rmi.RemoteException;

 class Component implements RMI_Interface2 {
  private int tid;
  private int ntid;
  private int nntid;
  private boolean active;
  private int port;
  private String upstream_neighbor;
  private String downstream_neighbor;


  public Component(int tid, String downstream_neighbor ) {
    this.tid = tid;
    this.downstream_neighbor = downstream_neighbor;
  }

  @Override public int retrieve_tid() throws RemoteException {
    return this.tid;
  }

  @Override public int retrieve_ntid() throws RemoteException {
    return this.ntid;
  }

  @Override public int retrieve_nntid() throws RemoteException {
    return this.nntid;
  }

  @Override public boolean is_active() throws RemoteException {
    return this.active;
  }

  @Override public void update_tid(int tid) throws RemoteException {
    this.tid = tid;
  }

  @Override public void update_ntid(int ntid) throws RemoteException {
    this.ntid = ntid;
  }

  @Override public void update_nntid(int nntid) throws RemoteException {
    this.nntid = nntid;
  }

  @Override public boolean check_condition() throws RemoteException {
     if(this.ntid >= this.tid && this.nntid <= this.ntid){

       //update the tid
       this.tid = this.ntid;
     } else {
       //update activeness
       this.active = false;
     }

     //return status
     return active;
  }

  @Override public void start_election() throws RemoteException {

  }

  @Override public String test_hello_world() throws RemoteException {
    return "Hello world!";
  }
}
