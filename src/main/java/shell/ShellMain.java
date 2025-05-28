package shell;

import me.partlysunny.Main;
import me.partlysunny.Testing;
import org.jline.jansi.AnsiConsole;

import java.io.IOException;

public class ShellMain {

    public static void main(String[] args) throws IOException {
        // workaround for jAnsi problems, (backspace and arrow keys not working)
        AnsiConsole.systemUninstall();
        Main.main(args);
//        Testing.main(args);
    }

}
