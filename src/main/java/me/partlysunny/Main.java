package me.partlysunny;

import me.partlysunny.network.client.ClientLoop;
import me.partlysunny.network.server.ServerLoop;
import org.jline.jansi.Ansi;
import org.jline.jansi.AnsiConsole;

import java.io.IOException;
import java.util.Objects;

public class Main {

    public static Ansi C = Ansi.ansi();

    public static void main(String[] args) throws IOException {
        AnsiConsole.systemInstall();
        if (Objects.equals(args[0], "server")) {
            ServerLoop.start(args);
        } else {
            ClientLoop.start(args);
        }
    }


}
