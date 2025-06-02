package me.partlysunny.network.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import me.partlysunny.TUtil;
import me.partlysunny.network.Packet;
import me.partlysunny.network.client.ClientLoop;

public class ClientboundIdResponse implements Packet {

    private int value;
    private boolean success;

    public ClientboundIdResponse() {}

    public ClientboundIdResponse(int value, boolean isSuccess) {
        this.value = value;
        this.success = isSuccess;
    }

    @Override
    public void load(ByteBuf buf) {
        value = buf.readInt();
        success = buf.readBoolean();
    }

    @Override
    public ByteBuf serialise(ByteBuf base) {
        base.writeInt(value);
        base.writeBoolean(success);
        return base;
    }

    @Override
    public void receive(ChannelHandlerContext ctx) {
        if (!success) {
            ctx.close();
            System.exit(0);
        }
        ClientLoop.ID = value;
        TUtil.debug("Registered client id " + value);
    }
}
