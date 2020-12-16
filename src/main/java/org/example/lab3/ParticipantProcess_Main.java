package org.example.lab3;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ParticipantProcess_Main {

    public static void main(String args[]) throws IOException {
        System.out.println("New process opened with id = " + args[0] + ", v = " + args[1] + ", h(onest) or t(raitor) = " + args[2] );

        Registry registry = LocateRegistry.getRegistry(1099);
        String rmi_name = "rmi://localhost/process-" + args[0];

        //check if this participant is honest
        if(args[2].equals("h")){
            ParticipantProcess participantProcess = new ParticipantProcess(rmi_name, Integer.parseInt(args[1]));
            ParticipantProcess_Interface stub = (ParticipantProcess_Interface) UnicastRemoteObject.exportObject(participantProcess, 0);
            registry.rebind(rmi_name, stub);
        } else {
            TraitorProcess traitorProcess = new TraitorProcess(rmi_name, Integer.parseInt(args[1]));
            ParticipantProcess_Interface stub = (ParticipantProcess_Interface) UnicastRemoteObject.exportObject(traitorProcess, 0);
            registry.rebind(rmi_name, stub);
        }

    }

}
