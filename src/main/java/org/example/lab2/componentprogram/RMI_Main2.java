package org.example.lab2.componentprogram;

import com.sun.org.apache.xerces.internal.util.ShadowedSymbolTable;
import org.example.lab2.componentprogram.Component;
import org.example.lab2.componentprogram.RMI_Interface2;

import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class RMI_Main2 {

  public static String host_name = "Naqib";
  public static String host_ip = "80.114.173.96"; //naqib is host
  public static String other_ip = "86.94.144.88"; //dirk is the other
  public static Registry registry;

  public static void main(String args[]){

    try{

      //create and install a security manager
      if(System.getSecurityManager() == null) {
        System.setSecurityManager(new RMISecurityManager());
      }

      //create or get the registry
      if(host_name.equals("Naqib")){
        registry = LocateRegistry.createRegistry(1099);
      } else {
        registry = LocateRegistry.getRegistry(other_ip, 1099);
      }

      //create 4 unique tid
      Queue<Integer> q = new LinkedList<>();
      Random random = new Random();
      while(q.size() != 4){
        int possible_tid = random.nextInt(500);
        if(!q.contains(possible_tid)){
          q.add(possible_tid);
        }
      }

      //Create four components and add them to the registry (later each component will start process in a thread)
      ArrayList<Component> components = new ArrayList<>();

      //first component
      Component first_component = new Component(q.remove(), "rmi://" + other_ip + ":1099/component3", "rmi://" + host_ip + ":1099/component1");
      components.add(first_component);

      for (int i = 1; i < 3; i++){
        Component current_component = new Component(q.remove(), "rmi://" + host_ip + ":1099/component-" + (i-1),  "rmi://" + host_ip + ":1099/component-" + (i+1));
        RMI_Interface2 stub = (RMI_Interface2) UnicastRemoteObject.exportObject(current_component, 0);
        registry.bind("rmi://" + host_ip + ":1099/component-" + i, stub);
        components.add(current_component);
      }

      //last component
      Component last_component = new Component(q.remove(), "rmi://" + host_ip + ":1099/component2", "rmi://" + other_ip + ":1099/component0");
      components.add(last_component);

      Thread.sleep(30000);
      RMI_Interface2 stub = (RMI_Interface2) registry.lookup("rmi://" + other_ip + ":1099/component3");
      System.out.println(stub.test_hello_world());

    } catch (Exception e){
      System.err.println("Server Exception: " + e.toString());
      e.printStackTrace();
    }
  }


  public void connect_last_and_first_componenent(){

  }
}
