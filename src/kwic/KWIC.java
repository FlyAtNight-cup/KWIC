package kwic;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.io.*;
import java.util.Arrays;

public class KWIC {
    public static void main(String[] args)  {
        try {
            long start = System.currentTimeMillis();
            Pipe input = new Pipe();
            Pipe output = new Pipe();
            String path = args[0];
            File in = new File(path,"kwicold.txt");
            File out = new File(path,"kwicnew.txt");
            FileReader fileReader = new FileReader(in);
            OutputStream outputStream = new FileOutputStream(out);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String message;
            while((message = bufferedReader.readLine()) != null) {
                input.write(message);
                Filter filter = new Filter(input,output);
                Thread thread = new Thread(filter);
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String[] result = new String[output.size()];
            for(int i=0;i<output.size();i++) {
                result[i] = output.read()+"\n";
            }
            Arrays.sort(result);
            for(int i=0;i<output.size();i++) {
                outputStream.write(result[i].getBytes());
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Use " + (endTime-start) + " ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
