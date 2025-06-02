package me.partlysunny.network.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.partlysunny.TUtil;
import me.partlysunny.game.GameHandler;
import me.partlysunny.network.Packet;

public class ServerPacketHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Packet packet) {
            TUtil.debug("ServerPacketHandler received packet: " + packet);
            packet.receive(ctx);
        } else {
            throw new IllegalStateException("Packet was not a packet???");
        }
    }
}
