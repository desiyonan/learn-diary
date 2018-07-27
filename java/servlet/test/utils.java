package test;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

public class utils {
    public static void appendS(StringBuilder strBd, String ...strs){
        Arrays.stream(strs).forEach(strBd::append);
    }
    public static void print(Writer out, String str) throws IOException {
        out.write(str);
        System.out.print(str);
    }
}
