package me.partlysunny.network.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import me.partlysunny.TUtil;
import me.partlysunny.game.GameState;
import me.partlysunny.game.Map;
import me.partlysunny.network.PacketDecoder;
import me.partlysunny.network.PacketEncoder;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class ClientLoop {

    public static GameState state;
    // will be unique, when connected we will be given it by the server
    public static int ID = 0;
    public static int cameraX = 0;
    public static int cameraY = 0;

    public static void start(String[] args) throws IOException {
        state = new GameState();
        try (Terminal terminal = TerminalBuilder.builder()
                .system(true)
                .build()) {
            LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();
            TUtil.T = terminal;
            new PointerWidget(reader);

            String host = args[1];
            int port = Integer.parseInt(args[2]);

            EventLoopGroup group = new NioEventLoopGroup();

            try {
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true).handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new PacketDecoder(), new PacketEncoder(), new ClientPacketHandler());
                    }
                });

                // Connect to the server
                ChannelFuture future = bootstrap.connect(host, port).sync();

                System.out.println("Connected to server at " + host + ":" + port);
                // temporary behaviour before we have shared map etc
                Map map = new Map(50, 40);
                state.map = map;
                state.scores.put(ID, 0);

                while (true) {
                    refresh();
                    String line = reader.readLine();
                    if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
                        break;
                    }
                }

                future.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Cleanup resources if needed
        }
    }

    public static void refresh() {
        Terminal terminal = TUtil.T;
        Map map = state.map;
        cameraX = Math.max(cameraX, 0);
        cameraY = Math.max(cameraY, 0);
        cameraX = Math.min(cameraX, map.getWidth() - 1);
        cameraY = Math.min(cameraY, map.getHeight() - 1);
        terminal.writer().print("\n".repeat(60) + map.render(cameraX, cameraY));
        terminal.flush();
    }

}
