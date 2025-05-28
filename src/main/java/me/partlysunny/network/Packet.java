package me.partlysunny.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public interface Packet {

    int payloadSize();

    // load will be what happens when we receive this packet. we will generate a packet based on the buffer data
    void load(ByteBuf buf);

    // serialise will happen when we send the packet, we generate a buffer based on what data is currently in the packet
    ByteBuf serialise(ByteBuf base);

    void receive(ChannelHandlerContext ctx);

}
