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

    @Override
    public String toString() {
        return String.format("{%s: w=%s r=%s}", mt, w, r);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Message other = (Message) obj;
        return mt == other.mt && r == other.r && w == other.w;
    }
}
