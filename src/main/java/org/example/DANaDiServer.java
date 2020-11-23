package org.example;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * The remote algorithm class implements the remote interface,
 * in which the actual work of a single process of the distributed algorithm is performed.
 */
public class DANaDiServer implements DANaDiRMIInterface {


    /**
     * Variables
     */
    private ArrayList<Integer> V;
    String m = "";

    public DANaDiServer() {}

    public String sayHello() {
        return "Hello, world!";
    }

    @Override public int registerClient() throws RemoteException {
        this.V.add(0);
        return V.size() - 1;
    }

    /**
     *
     * @param m
     * @param vSender
     * @return
     * @throws RemoteException
     */
    @Override public String broadcastMessage(String m, int[] vSender) throws RemoteException {

        return "";
    }

    @Override public String receiveMessage(String m, int[] vSender) throws RemoteException {
        return m;
    }

    @Override public String deliverMessage(String m) throws RemoteException {
        return m;
    }

    public static void main(String args[]) {

        try {
            // Create and install a security manager
            if (System.getSecurityManager() == null) {System.setSecurityManager(new RMISecurityManager());}

            DANaDiServer obj = new DANaDiServer();
            DANaDiRMIInterface stub = (DANaDiRMIInterface) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("rmi://localhost:1099/remote-object", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
