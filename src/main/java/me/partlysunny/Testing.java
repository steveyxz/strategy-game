package me.partlysunny;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.Widget;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Testing {
    public static void main(String[] args) throws IOException {
        Terminal terminal = TerminalBuilder.builder().build();

        // Create a line reader
        LineReader reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .build();

        // Create a custom widget to insert the current timestamp
        Widget insertTimestampWidget = () -> {
            // Get the current timestamp
            String timestamp = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Insert the timestamp at the current cursor position
            reader.getBuffer().write(timestamp);

            // Return true to indicate success
            return true;
        };

        // Register the widget with the line reader
        reader.getWidgets().put("insert-timestamp", insertTimestampWidget);

        // Bind the widget to a key combination (Alt+T)
        reader.getKeyMaps().get(LineReader.MAIN).bind(
                insertTimestampWidget,
                "\033t");  // Escape followed by 't' represents Alt+T

        // Display instructions
        terminal.writer().println("Custom widget example:");
        terminal.writer().println("  Press Alt+T to insert the current timestamp");
        terminal.writer().println("\nType some text and try the custom widget:");
        terminal.writer().flush();

        // Read lines until "exit" is entered
        String line;
        while (!(line = reader.readLine("prompt> ")).equalsIgnoreCase("exit")) {
            terminal.writer().println("You entered: " + line);
        }

        terminal.close();
    }
}