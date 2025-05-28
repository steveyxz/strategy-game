package me.partlysunny;

import org.jline.terminal.Terminal;

public class TUtil {

    public static Terminal T;

    private static boolean debug = true;

    public static void clear() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n".repeat(100));
        T.writer().print(sb.toString());
    }

    public static void debug(String msg) {
        if (debug) {
            T.writer().println(msg);
            T.writer().flush();
        }
    }



}
