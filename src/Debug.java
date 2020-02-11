import java.io.*;

//helper class for debug output
public class Debug{
    public static boolean debug = false;

    public static PrintStream out;
    
    static{
        if(debug){
            out = System.out;
        } else{
            out = new PrintStream(OutputStream.nullOutputStream());
        }
    }
}