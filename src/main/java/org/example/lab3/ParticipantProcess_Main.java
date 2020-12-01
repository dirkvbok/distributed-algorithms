package org.example.lab3;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ParticipantProcess_Main {

    public static void main(String args[]) throws IOException {
        System.out.println("New process opened with v = " + args[1]);

        Registry registry = LocateRegistry.getRegistry(1099);
        String rmi_name = "rmi://localhost/process-" + args[0];

        ParticipantProcess participantProcess = new ParticipantProcess(rmi_name, Integer.parseInt(args[1]));
        ParticipantProcess_Interface stub = (ParticipantProcess_Interface) UnicastRemoteObject.exportObject(participantProcess, 0);
        registry.rebind(rmi_name, stub);
    }

}
