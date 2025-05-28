package me.partlysunny.network.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import me.partlysunny.network.Packet;
import me.partlysunny.network.server.ClientboundIdResponse;
import me.partlysunny.network.server.ServerLoop;

public class ServerboundIdRequest implements Packet {

    @Override
    public int payloadSize() {
        return 0;
    }

    @Override
    public void load(ByteBuf buf) {
    }

    @Override
    public ByteBuf serialise(ByteBuf base) {
        return base;
    }

    @Override
    public void receive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new ClientboundIdResponse(ServerLoop.IDX++));
    }
}
