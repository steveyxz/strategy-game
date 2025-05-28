package me.partlysunny.network.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.partlysunny.TUtil;
import me.partlysunny.network.Packet;

public class ClientPacketHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(new ServerboundIdRequest());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Packet packet) {
            TUtil.debug("ClientPacketHandler received packet: " + packet);
            packet.receive(ctx);
        } else {
            throw new IllegalStateException("Packet is not packet???");
        }
    }
}
