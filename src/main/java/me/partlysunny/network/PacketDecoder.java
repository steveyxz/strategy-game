package me.partlysunny.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    int packet = -1;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (packet == -1) {
            if (byteBuf.readableBytes() < 4) {
                return;
            }
            packet = byteBuf.readInt();
        }
        Packet p = PacketRegistry.generate(packet, byteBuf);
        if (p == null) return;
        list.add(p);
        packet = -1;
    }
}
