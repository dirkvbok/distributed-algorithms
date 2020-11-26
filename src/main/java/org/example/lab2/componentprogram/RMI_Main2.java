package org.example.lab2.componentprogram;

import com.sun.org.apache.xerces.internal.util.ShadowedSymbolTable;
import org.example.lab2.componentprogram.Component;
import org.example.lab2.componentprogram.RMI_Interface2;

import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.net.InetAddress;

public class RMI_Main2 {

  public static List<Component> components;
  public static String host_ip = "80.114.173.96"; //Naqib is the host
  public static String other_ip = "86.94.144.88"; //Dirk is the other
  public static int[] main1_ports = {3111, 3116, 3114, 3120};
  public static int[] main2_ports = {3112, 3115, 3119, 3123};
  public static int[] main1_ids = {3, 11, 4, 9};
  public static int[] main2_ids = {6, 2, 5, 12};


//  public static String host_name = "Naqib";
//  public static String host_ip = "80.114.173.96"; //naqib is host
//  public static String other_ip = "86.94.144.88"; //dirk is the other
//  public static Registry registry;

  public static void main(String args[]){





//    try{
//
//      //create and install a security manager
//      if(System.getSecurityManager() == null) {
//        System.setSecurityManager(new RMISecurityManager());
//      }
//
//      //create or get the registry
//      if(host_name.equals("Naqib")){
//        registry = LocateRegistry.createRegistry(1099);
//        System.out.println("registry gegevens: " + registry.toString());
//      } else {
//        registry = LocateRegistry.getRegistry(other_ip, 1099);
//      }
//
//      //create 4 unique tid
//      Queue<Integer> q = new LinkedList<>();
//      Random random = new Random();
//      while(q.size() != 4){
//        int possible_tid = random.nextInt(500);
//        if(!q.contains(possible_tid)){
//          q.add(possible_tid);
//        }
//      }
//
//      System.setProperty("java.rmi.server.hostname", "80.114.173.96");
//
//      //Create four components and add them to the registry (later each component will start process in a thread)
//      ArrayList<Component> components = new ArrayList<>();
//
//      //first component
//      Component first_component = new Component(q.remove(), "rmi://" + other_ip + ":1099/component-3", "rmi://" + host_ip + ":1099/component-1");
//      RMI_Interface2 stub_first = (RMI_Interface2) UnicastRemoteObject.exportObject(first_component, 0);
//      registry.rebind("rmi://" + host_ip + ":1099/component-0" , stub_first);
//      components.add(first_component);
//
//      for (int i = 1; i < 3; i++){
//        Component current_component = new Component(q.remove(), "rmi://" + host_ip + ":1099/component-" + (i-1),  "rmi://" + host_ip + ":1099/component-" + (i+1));
//        RMI_Interface2 stub = (RMI_Interface2) UnicastRemoteObject.exportObject(current_component, 0);
//        registry.rebind("rmi://" + host_ip + ":1099/component-" + i, stub);
//        components.add(current_component);
//      }
//
//      //last component
//      Component last_component = new Component(q.remove(), "rmi://" + host_ip + ":1099/component-2", "rmi://" + other_ip + ":1099/component-0");
//      RMI_Interface2 stub_last = (RMI_Interface2) UnicastRemoteObject.exportObject(last_component, 0);
//      registry.rebind("rmi://" + host_ip + ":1099/component-3" , stub_last);
//      components.add(last_component);
//
//
//      System.out.println("done with server part");
//
//    } catch (Exception e){
//      System.err.println("Server Exception: " + e.toString());
//      e.printStackTrace();
//    }
  }

}
