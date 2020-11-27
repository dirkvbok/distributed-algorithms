package org.example.lab2.componentprogram;

import java.io.IOException;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;


public class RMI_Main1 {

  public static List<Component> components;
  public static String host_ip = "192.168.1.100"; //Naqib is the host
  public static String other_ip = "86.94.144.88"; //todo DIRK: make this what is your private ipv4 number
  public static int[] main1_ports = {3111, 3116, 3114, 3120};
  public static int[] main2_ports = {3112, 3115, 3119, 3123};
  public static int[] main1_ids = {3, 11, 4, 9};
  public static int[] main2_ids = {6, 2, 5, 12};


  public static void main(String args[]) throws IOException {


    for(int i = 0; i< 4; i++){

      try{

        //create and install a security manager
        if(System.getSecurityManager() == null) {
          System.setSecurityManager(new RMISecurityManager());
        }

        // if its the last component and its neighbor is from different process
        if(i == 3){
          int port_me = main1_ports[i];
          int port_neighbor = main2_ports[0];

          //execute external program
          Runtime.getRuntime().exec("rmiregistry " + Integer.toString(port_me));
          Registry registry = LocateRegistry.createRegistry(port_me);
          String downstream_neighbor = "rmi://" + other_ip + ":" + port_neighbor;
          Component component = new Component(main1_ids[i], downstream_neighbor);
          registry.rebind("rmi://" + host_ip + port_me + "/component", component);        //todo: als dit niet lukt, probeer met Naming.rebind
          components.add(component);

        } else {
          int port_me = main1_ports[i];
          int port_neighbor = main1_ports[i+1];

          //execute external program
          Runtime.getRuntime().exec("rmiregistry " + Integer.toString(port_me));
          Registry registry = LocateRegistry.createRegistry(port_me);
          String downstream_neighbor = "rmi://" + host_ip + ":" + port_neighbor;
          Component component = new Component(main1_ids[i], downstream_neighbor);
          registry.rebind("rmi://" + host_ip + port_me + "/component", component);        //todo: als dit niet lukt, probeer met Naming.rebind
          components.add(component);
        }

      } catch (Exception e){
        e.printStackTrace();
      }

    }

//    int[] main1_ids= new int[4];
//    int[] main2_ids= new int[4];
    //create 8 random unique ids
//    Queue<Integer> q = new LinkedList<>();
//    Random random = new Random();
//    while(q.size() != 8){
//      int possible_tid = random.nextInt(500);
//      if(!q.contains(possible_tid)){
//        q.add(possible_tid);
//      }
//    }
//
//    //add the even indexes to the first main list and the odds to the second list
//    for(int i=0; i<8; i++){
//      if((i%2) == 0){
//        main1_ids[i] = q.remove();
//      } else {
//        main2_ids[i] = q.remove();
//      }
//    }





  }







}
