package me.partlysunny.network.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import me.partlysunny.network.PacketDecoder;
import me.partlysunny.network.PacketEncoder;

public class ServerLoop {

    public static int IDX = 1;

    public static void start(String[] args) {
        int port = Integer.parseInt(args[1]);
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
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
            // Bind the server to a port (e.g., 8080)
            ChannelFuture f = bootstrap.bind(port).sync();
            System.out.println("Server started successfully on port " + port);
            // here we do the server loop?
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
