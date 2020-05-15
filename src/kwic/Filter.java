package kwic;

public class Filter implements Runnable{
    private final Pipe word;
    private final Pipe in;
    private final Pipe out;
    protected Filter(Pipe in, Pipe out) {
        word = new Pipe();
        this.in = in;
        this.out = out;
    }

    protected void transform() {
        LineToWord();
        WordToLine();
    }

    @Override
    public void run() {
        this.transform();
    }
    private void LineToWord() {
        String word;
        String line = in.read();
        for(int i = 0, j = 0; i < line.length(); i++) {
            if(line.charAt(i) == ' ') {
                word = line.substring(j,i);
                this.word.write(word);
                j=i+1;
            }
            else if(i == line.length()-1) {
                word = line.substring(j,i+1);
                this.word.write(word);
            }
        }
    }

    private void WordToLine() {
        String[] words = new String[word.size()];
        String message;
        for(int i = 0; i< word.size(); i++) {
            words[i] = word.read();
        }
        for(int i = 0; i< word.size(); i++) {
            message = "";
            for(int j = i; j< word.size()+i; j++) {
                message = message + " " + words[j% word.size()];
            }
            message = message.substring(1);
            out.write(message);
        }
    }
}
