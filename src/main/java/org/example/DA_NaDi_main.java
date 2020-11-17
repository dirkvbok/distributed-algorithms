package org.example;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

public class DA_NaDi_main {
    public static void main( String[] args ) {
        System.out.println( "Hello World!" );

        //Create and install a security manager
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        // Register this host in distributed algorithm
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
