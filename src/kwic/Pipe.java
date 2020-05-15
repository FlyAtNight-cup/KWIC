package kwic;

import java.util.ArrayDeque;

public class Pipe {
    private ArrayDeque<String> messages;
    private int size;
    public Pipe(){
        messages = new ArrayDeque<>();
        size = 0;
    }
    public void write(String message) {
        messages.addFirst(message);
        size++;
    }

    public String read() {
        if(size()==0)
            return null;
        else {
            String message = messages.getLast();
            messages.pollLast();
            return message;
        }

    }
    public int size() {
        return size;
    }
}
