//package org.example.lab2.componentprogram;
//
//
//import java.rmi.RMISecurityManager;
//import java.rmi.RemoteException;
//import java.rmi.registry.LocateRegistry;
//import java.rmi.registry.Registry;
//import java.util.*;
//
//public class RMI_Main2 {
//
//  public static List<Component> components;
//  public static String host_ip = "80.114.173.96"; //Naqib is the host
//  public static String other_ip = "86.94.144.88"; //Dirk is the other
//  public static int[] main1_ports = {3111, 3116, 3114, 3120};
//  public static int[] main2_ports = {3112, 3115, 3119, 3123};
//  public static int[] main1_ids = {3, 11, 4, 9};
//  public static int[] main2_ids = {6, 2, 5, 12};
//
//
//  public static void main(String args[]) throws RemoteException {
//
//    for(int i = 0; i< 4; i++){
//
//      try{
//
//        //create and install a security manager
//        if(System.getSecurityManager() == null) {
//          System.setSecurityManager(new RMISecurityManager());
//        }
//
//        // if its the last component and its neighbor is from different process
//        if(i == 3){
//          int port_me = main1_ports[i];
//          int port_neighbor = main1_ports[0];
//
//          //execute external program
//          Runtime.getRuntime().exec("rmiregistry " + Integer.toString(port_me));
//          Registry registry = LocateRegistry.createRegistry(port_me);
//          String downstream_neighbor = "rmi://" + host_ip + ":" + port_neighbor;
//          Component component = new Component(main2_ids[i], downstream_neighbor);
//          registry.rebind("rmi://" + other_ip + port_me + "/component", component);        //todo: als dit niet lukt, probeer met Naming.rebind
//          components.add(component);
//
//        } else {
//          int port_me = main1_ports[i];
//          int port_neighbor = main2_ports[i+1];
//
//          //execute external program
//          Runtime.getRuntime().exec("rmiregistry " + Integer.toString(port_me));
//          Registry registry = LocateRegistry.createRegistry(port_me);
//          String downstream_neighbor = "rmi://" + other_ip + ":" + port_neighbor;
//          Component component = new Component(main2_ids[i], downstream_neighbor);
//          registry.rebind("rmi://" + other_ip + port_me + "/component", component);        //todo: als dit niet lukt, probeer met Naming.rebind
//          components.add(component);
//        }
//
//      } catch (Exception e){
//        e.printStackTrace();
//      }
//
//    }
//
//    //while there are more than 1 components active -> perform election
//    while(components.size() != 1){
//      perform_election();
//    }
//
//    System.out.println("the elected component is " + components.get(0));
//
//  }
//
//
//  //todo: we doen nog nergens lookup ofzo. als we de volgende component willen hebben moeten we eigenlijk lookup("rmi//"+host+":"+next port + "/component" doen ofzo
//  public static void perform_election() throws RemoteException {
//
//    //STEP 1: sends components tid to its downstream neighbor
//    for(int i = 0; i< components.size(); i++){
//
//      //handle last component -> set the first components ntid to last components tid
//      if(i == components.size() - 1){
//        components.get(0).update_ntid(components.get(i).retrieve_tid());
//      } else{
//        components.get(i+1).update_ntid(components.get(i).retrieve_tid());
//      }
//    }
//
//    //STEP 2: send max(tid, ntid) to its neighbor
//    for(int i = 0; i< components.size(); i++){
//
//      //handle last component -> set the first components ntid to last components tid
//      if(i == components.size() - 1){
//        components.get(0).update_nntid(Math.max(components.get(i).retrieve_tid(), components.get(i).retrieve_ntid()));
//      } else{
//        components.get(i+1).update_nntid(Math.max(components.get(i).retrieve_tid(), components.get(i).retrieve_ntid()));
//      }
//    }
//
//    //STEP 3: checks the condition and update status
//    for(int i = 0; i < components.size(); i++){
//      components.get(i).check_condition();
//    }
//
//    //STEP 4: remove the components that are inactive
//    for(int i=0; i< components.size(); i++){
//      if (!components.get(i).is_active()){
//        components.remove(i);
//      }
//    }
//
//    //STEP 5: re-create a ring with all the parts
//    //todo: refactor de code in de main zodat die in een aparte method komt "createRing" zodat we die shit hier weer aan kunnen roepen ofzo.
//
//
//
//  }
//
//}
