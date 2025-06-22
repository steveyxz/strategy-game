package me.partlysunny.game.ui.console;

import me.partlysunny.TUtil;
import me.partlysunny.game.GameState;
import me.partlysunny.game.ui.Renderer;
import me.partlysunny.network.client.ClientLoop;
import me.partlysunny.network.client.PointerWidget;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class ConsoleRenderer implements Renderer {
    @Override
    public void refresh(GameState newState) {
        Terminal terminal = TUtil.T;
        if (newState == null) {
            terminal.writer().println("Waiting for server to start...");
            terminal.writer().println("Type `start` in the server");
            terminal.writer().flush();
            return;
        }

        terminal.writer().print("\n".repeat(60) + newState.gameMap.render(ClientLoop.ID));
        terminal.flush();
    }

    @Override
    public void frame() {

    }

    @Override
    public void run() {
        try (Terminal terminal = TerminalBuilder.builder()
                .system(true)
                .build()) {
            LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();
            TUtil.T = terminal;
            new PointerWidget(reader);
            while (true) {
               String s = reader.readLine();
               ClientLoop.refresh();
               if (s.equalsIgnoreCase("exit")) {
                   return;
               }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
