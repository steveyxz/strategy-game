package me.partlysunny.network.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import me.partlysunny.game.GameHandler;
import me.partlysunny.network.Packet;
import me.partlysunny.network.server.ClientboundIdResponse;
import me.partlysunny.network.server.ServerLoop;

public class ServerboundIdRequest implements Packet {

    @Override
    public void load(ByteBuf buf) {
    }

    @Override
    public ByteBuf serialise(ByteBuf base) {
        return base;
    }

    @Override
    public void receive(ChannelHandlerContext ctx) {
        boolean success = GameHandler.addPlayer(ServerLoop.IDX, ctx);
        ctx.writeAndFlush(new ClientboundIdResponse(ServerLoop.IDX++, success));
        // register this player in my server code
    }
}
