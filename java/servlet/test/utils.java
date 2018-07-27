package test;

import java.util.Arrays;

public class utils {
    public static void appendS(StringBuilder strBd, String ...strs){
        Arrays.stream(strs).forEach(strBd::append);
    }
}
