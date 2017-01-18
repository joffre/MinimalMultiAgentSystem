package output;

import core.PropertiesReader;

import java.io.OutputStream;
import java.util.List;

/**
 * Created by Geoffrey on 18/01/2017.
 */
public class Log {

    private static List<OutputStream> outputs;

    private static Log instance;

    private Log(){
    }

    private static Log getInstance(){
        if(instance == null) instance = new Log();
        return instance;
    }

    public static void info(String message){
        if(PropertiesReader.getInstance().traceIsVisible()) getInstance().print(message);
    }

    public void print(Object element){
        System.out.println(element.toString());
    }
}
