package org.example.lab3;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ParticipantProcess implements ParticipantProcess_Interface {

    String rmi_my_name;
    int v;
    int r;
    int nrReceivedMessagesN;
    int nrReceivedMessagesP;
    int f;
    int n;
    int nrW0 ;
    int nrW1;
    boolean decided;

    public ParticipantProcess(String rmi_my_name, int v) {
        System.out.println("v in constructor: " + v);
        this.rmi_my_name = rmi_my_name;
        this.v = v;
        this.r = 1;
        if (v == 0) {
            this.nrW0 = 1;
            this.nrW1 = 0;
        } else {
            this.nrW0 = 0;
            this.nrW1 = 1;
        }
        nrReceivedMessagesN = 1;
        nrReceivedMessagesP = 1;
        this.decided = false;
    }

    @Override
    public void start() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(1099);
        n = registry.list().length;
        f = (int) Math.floor((n - 1) / 5.0);
        System.out.println("n: " + n + ", f: " + f);


        System.out.println("broadcast message with v = " + v);
        broadcast(new Message(MessageType.NOTIFICATION, r, v));
    }

    @Override
    public void broadcast(Message m) throws RemoteException, NotBoundException {
        System.out.println("broadcasting");
        Registry registry = LocateRegistry.getRegistry(1099);
        String[] rmi_list = registry.list();
        for(String rmi_name : rmi_list) {
            if (rmi_name != rmi_my_name) {
                ParticipantProcess_Interface stub = (ParticipantProcess_Interface) registry.lookup(rmi_name);
                System.out.println("send message with v = " + m.w);
                stub.receive(m);
            }
        }
    }

    @Override
    public void receive(Message message) throws RemoteException, NotBoundException {
        System.out.println("Receiving: " + message.w);

        // Must be in same round
        if(message.r == r) {

            switch(message.mt) {

                case NOTIFICATION:
                    nrReceivedMessagesN++;
                    updateNrW(message.w);

                    // Await (n-f) messages of the form (N,r,*)
                    if (nrReceivedMessagesN >= (n-f)) {

                        // If majority has agreed on w, broadcast proposal
                        if ((Math.max(nrW0, nrW1) > (n + f) / 2)) {
                            broadcast(new Message(MessageType.PROPOSAL, r, maxW(nrW0, nrW1)));
                        } else {
                            broadcast(new Message(MessageType.PROPOSAL, r, -1));
                        }
                    }
                    break;

                case PROPOSAL:
                    nrReceivedMessagesP++;
                    break;
            }
        }

        // Decision phase
    }

    private int maxW(int nrW0, int nrW1) {
        if (nrW0 > nrW1) {
            return 0;
        }
        if (nrW1 > nrW0) {
            return 1;
        }
        return -1;
    }

    private void updateNrW(int w) {
        if (w == 0) {
            nrW0++;
        } else if(w == 1) {
            nrW1++;
        }
    }







//    public void notificationPhase() {
//
//    }
//
//    public void proposalPhase() {
//
//    }
//
//    public void decisionPhase() {
//
//    }
}
