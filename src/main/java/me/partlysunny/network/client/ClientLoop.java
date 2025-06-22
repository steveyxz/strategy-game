package me.partlysunny.network.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import me.partlysunny.TUtil;
import me.partlysunny.game.map.GameMap;
import me.partlysunny.game.GameState;
import me.partlysunny.ui.Renderer;
import me.partlysunny.ui.window.SwingRenderer;
import me.partlysunny.network.PacketDecoder;
import me.partlysunny.network.PacketEncoder;

public class ClientLoop {

    public static GameState state;
    // will be unique, when connected we will be given it by the server
    public static int ID = 0;
    public static int cameraX = 0;
    public static int cameraY = 0;
    public static int selectedX = 0;
    public static int selectedY = 0;

    public static Renderer renderer;

    public static void start(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        String host = args[1];
        int port = Integer.parseInt(args[2]);
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

            TUtil.debug("Connected to server at " + host + ":" + port);

            renderer = new SwingRenderer();
            new Thread(renderer).start();

            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void refresh() {
        if (state == null) {
            renderer.refresh(null);
            return;
        }
        GameMap gameMap = state.gameMap;
        selectedX = Math.max(selectedX, 0);
        selectedY = Math.max(selectedY, 0);
        selectedX = Math.min(selectedX, gameMap.getWidth() - 1);
        selectedY = Math.min(selectedY, gameMap.getHeight() - 1);
        cameraX = Math.max(cameraX, selectedX - GameMap.DISPLAY_SIZE);
        cameraY = Math.max(cameraY, selectedY - GameMap.DISPLAY_SIZE);
        cameraX = Math.min(cameraX, selectedX + GameMap.DISPLAY_SIZE);
        cameraY = Math.min(cameraY, selectedY + GameMap.DISPLAY_SIZE);
        renderer.refresh(state);
    }

}
