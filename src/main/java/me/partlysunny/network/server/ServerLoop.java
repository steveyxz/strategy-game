package me.partlysunny.network.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import me.partlysunny.TUtil;
import me.partlysunny.game.GameHandler;
import me.partlysunny.game.GameMap;
import me.partlysunny.network.PacketDecoder;
import me.partlysunny.network.PacketEncoder;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class ServerLoop {

    public static int IDX = 1;

    public static void start(String[] args) {
        int port = Integer.parseInt(args[1]);
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try (Terminal terminal = TerminalBuilder.builder()
                .system(true)
                .build()) {
            LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();
            // Initialize server components here
            // For example, set up the server channel, bind to a port, etc.
            // This is where you would handle incoming connections and messages.
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(io.netty.channel.socket.nio.NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new PacketDecoder(), new PacketEncoder(), new ServerPacketHandler());
                        }
                    })
                            .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            TUtil.T = terminal;
            // Bind the server to a port (e.g., 8080)
            ChannelFuture f = bootstrap.bind(port).sync();
            System.out.println("Server started successfully on port " + port);
            // here we do the server loop?
            while (true) {
                String line = reader.readLine();
                if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
                    break;
                }
                if (line.equalsIgnoreCase("start")) {
                    GameHandler.startGame();
                    break;
                }
            }
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Shutdown the event loop groups gracefully
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
