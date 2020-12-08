package org.example.lab3;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Random;

public class ParticipantProcess implements ParticipantProcess_Interface {

    Random random = new Random();
    String rmi_my_name;
    int v;
    int r;
    int f;
    int n;
    boolean decided;
    final RoundsMessageLists messagesN = new RoundsMessageLists(MessageType.NOTIFICATION);
    final RoundsMessageLists messagesP = new RoundsMessageLists(MessageType.PROPOSAL);

    public ParticipantProcess(String rmi_my_name, int v) {
        this.rmi_my_name = rmi_my_name;
        this.v = v;
        this.r = 0;
        this.decided = false;
    }

    @Override
    public void start() throws RemoteException, NotBoundException, InterruptedException {
        Registry registry = LocateRegistry.getRegistry(1099);
        n = registry.list().length;
        f = (int) Math.floor((n - 1) / 5.0);
        System.out.println("n: " + n + ", f: " + f);

        outerForever:
        while(true) {
            System.out.println("\n-----------------------------------");
            System.out.println("Round " + r);

            // Notification phase
            System.out.println("\nNOTIFICATION PHASE ROUND " + r + "\n");
            Message m = new Message(MessageType.NOTIFICATION, r, v);
            broadcast(m);

            awaitNMessages();

            // Proposal phase
            System.out.println("\nPROPOSAL PHASE ROUND " + r + "\n");
            MaxW maxWN = messagesN.getMaxW(r);
            if (maxWN.count > (n + f) / 2) {
                broadcast(new Message(MessageType.PROPOSAL, r, maxWN.w));
            } else {
                broadcast(new Message(MessageType.PROPOSAL, r, -1));
            }

            // if decided then STOP
            if (decided) {
                break outerForever;
            }

            awaitPMessages();

            // Decision phase
            System.out.println("\nDECISION PHASE ROUND " + r + "\n");
            MaxW maxWP = messagesP.getMaxW(r);
            if (maxWP.count > f) {
                v = maxWP.w;

                if (maxWP.count > 3f) {
                    System.out.println("Decided " + v);
                    decided = true;
                } else {
                    System.out.println("Not decided, current v: " + v);
                }
            } else {
                v = random.nextInt(2);
                System.out.println("Not decided, random chosen v: " + v);
            }

            r++;
        }

        System.out.println("\nDONE! \nAgreement on message w = " + v + "\n");
    }



    @Override
    public void broadcast(Message m) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(1099);
        String[] rmi_list = registry.list();

        // Store own message
        switch (m.mt) {
            case NOTIFICATION:
                messagesN.storeMessage(m);
            case PROPOSAL:
                messagesP.storeMessage(m);
        }

        for(String rmi_name : rmi_list) {
            if (!rmi_name.equals(rmi_my_name)) {
                // Wait random seconds between 1 - 4
                try {
                    Thread.sleep(random.nextInt(4000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ParticipantProcess_Interface stub = (ParticipantProcess_Interface) registry.lookup(rmi_name);
                System.out.println(m.toString() + " [SEND]");
                stub.receive(m);
            }
        }
    }

    @Override
    public synchronized void receive(Message message) {
        System.out.println(message.toString() + " [RECEIVE]");

        switch(message.mt) {

            case NOTIFICATION:
                messagesN.storeMessage(message);
                break;

            case PROPOSAL:
                messagesP.storeMessage(message);
                break;
        }

    }

    private void awaitNMessages() throws InterruptedException {
        System.out.println("Awaiting " + (n-f) + " N messages");

        while(true) {
            synchronized (messagesN) {
                if (messagesN.size(r) >= (n-f)) {
                    System.out.println("Received " + messagesN.size(r) + " N messages; breaking await loop");
                    break;
                }
            }
        }
    }

    private void awaitPMessages() throws InterruptedException {
        System.out.println("Awaiting " + (n-f) + " P messages");

        while(true) {
            synchronized (messagesP) {
                if (messagesP.size(r) >= (n-f)) {
                    System.out.println("Received " + messagesP.size(r) + " P messages; breaking await loop");
                    break;
                }
            }
        }
    }


}
