package org.example.lab3;

import java.io.Serializable;

public class Message implements Serializable {

    public MessageType mt;
    public int r;
    public int w;

    public Message(MessageType mt, int r, int w) {
        this.mt = mt;
        this.r = r;
        this.w = w;
    }
}
