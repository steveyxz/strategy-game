package me.partlysunny.network.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import me.partlysunny.TUtil;
import me.partlysunny.network.Packet;
import me.partlysunny.network.client.ClientLoop;

public class ClientboundIdResponse implements Packet {

    private int value;

    public ClientboundIdResponse() {}

    public ClientboundIdResponse(int value) {
        this.value = value;
    }

    @Override
    public int payloadSize() {
        return 4;
    }

    @Override
    public void load(ByteBuf buf) {
        value = buf.readInt();
    }

    @Override
    public ByteBuf serialise(ByteBuf base) {
        base.writeInt(value);
        return base;
    }

    @Override
    public void receive(ChannelHandlerContext ctx) {
        ClientLoop.ID = value;
        TUtil.debug("Registered client id " + value);
    }
}
