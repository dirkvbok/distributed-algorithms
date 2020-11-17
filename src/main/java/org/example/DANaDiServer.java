package org.example;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * The remote algorithm class implements the remote interface,
 * in which the actual work of a single process of the distributed algorithm is performed.
 */
public class DANaDiServer implements DANaDiRMIInterface {

    public DANaDiServer() {}

    public String sayHello() {
        return "Hello, world!";
    }

    public static void main(String args[]) {

        try {
            DANaDiServer obj = new DANaDiServer();
            DANaDiRMIInterface stub = (DANaDiRMIInterface) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("Hello", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
