package me.partlysunny.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public interface Packet extends Serialisable {

    void receive(ChannelHandlerContext ctx);

}
