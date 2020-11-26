package org.example.lab2;

import org.example.lab2.componentprogram.RMI_Interface2;
import org.example.lab2.componentprogram.Component;

import java.lang.reflect.Array;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class RMI_Orchestrator {

  public static void main(String args[]){

    try {

      //create and install a security manager
      if (System.getSecurityManager() == null) {
        System.setSecurityManager(new RMISecurityManager());
      }



      ArrayList<Component> components = new ArrayList<>();

      /*
      orchestrator:
      - verzamel ip addressen van  twee verschillende machines
      - faciliteer volgende communicatie tussen de twee machines
        - update tid nntid ntid etc.
      -


      1. send all the tid downstream to neighbor

       */


    } catch (Exception e){
      e.printStackTrace();
    }
  }


  public void register_component(String name, Registry registry, ArrayList<RMI_Interface2> components)
      throws RemoteException, NotBoundException {

    RMI_Interface2 stub = (RMI_Interface2) registry.lookup(name);
    components.add(stub);

  }

}
