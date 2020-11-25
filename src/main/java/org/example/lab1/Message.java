package org.example.lab1;

import java.io.Serializable;

public class Message implements Serializable {

    String m;
    int[] V;
    int senderIndex;

    public Message(String m, int[] V, int senderIndex) {
        this.m = m;
        this.V = V;
        this.senderIndex = senderIndex;
    }

    public String toString() {
        return "{ message: " + m + ", V: [" + V[0] + "," + V[1] + "," + V[2] + "] }";
    }
}

