package org.example.lab3;

import java.util.ArrayList;
import java.util.Collections;

public class RoundsMessageLists {

    ArrayList<ArrayList<Message>> messages = new ArrayList<>();
    MessageType messageType;

    public RoundsMessageLists(MessageType mt) {
        this.messageType = mt;
    }

    /**
     * Get size of message list for a certain round, add new round-list if needed.
     * @param round round to get listsize of
     * @return size of message list
     */
    public int size(int round) {
        addRoundIfNeeded(round);
        return messages.get(round).size();
    }

    /**
     * Store message to list of selected round, add new round-list if needed.
     * @param message Message to store
     */
    public synchronized void storeMessage(Message message) {
        System.out.println(message.toString() + " [STORE]");
        addRoundIfNeeded(message.r);
        messages.get(message.r).add(message);
    }

    /**
     * Check which message is received most, add new round-list if needed.
     * @param round round to check messages in
     * @return max w and its count
     */
    public MaxW getMaxW(int round) {
        addRoundIfNeeded(round);
        int w0Count = Collections.frequency(messages.get(round), new Message(messageType, round, 0));
        int w1Count = Collections.frequency(messages.get(round), new Message(messageType, round, 1));
        if (w0Count > w1Count)
            return new MaxW(0, w0Count);
        if (w1Count > w0Count)
            return new MaxW(1, w1Count);

        // Equal count, just choose 0
        return new MaxW(0, w0Count);
    }

    /**
     * Check if incoming round is already initialized in message list, initialize if needed.
     * @param round new round to check
     */
    private void addRoundIfNeeded(int round) {
        if (messages.size() < round+1) {
            messages.add(new ArrayList<Message>());
        }
    }

}
