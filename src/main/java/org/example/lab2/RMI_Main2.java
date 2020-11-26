package org.example.lab2;

import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;

public class RMI_Main2 {

  public static void main(String args[]){

    try{

      //create and install a security manager
      if(System.getSecurityManager() == null) {
        System.setSecurityManager(new RMISecurityManager());
      }

      //Bind the remote object's stub in the registry
      Registry registry = LocateRegistry.createRegistry(1099);

      ArrayList<Component> components = new ArrayList<>();

      Random random = new Random();
      for (int i = 0; i < 5; i++){
        int tid = random.nextInt(50); //todo: find a way to make it impossible to collide (get same tid)
        int port = 5000+i;
        Component component = new Component(tid, port);
        RMI_Interface2 stub = (RMI_Interface2) UnicastRemoteObject.exportObject(component, 0);
        registry.bind("rmi://localhost:1099/component-" + tid, stub);
        components.add(component);
      }

      //create the virtual ring
      for (int i = 1; i< components.size(); i++){
          Component previous = components.get(i-1);
          Component current = components.get(i);
          current.update_ntid(previous.retrieve_tid());
      }

    } catch (Exception e){
      System.err.println("Server Exception: " + e.toString());
      e.printStackTrace();
    }
  }
}
