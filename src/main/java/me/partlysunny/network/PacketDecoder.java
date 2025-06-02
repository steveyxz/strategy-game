package me.partlysunny.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    int packet = -1;
    int size = -1;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (packet == -1) {
            if (byteBuf.readableBytes() < 8) {
                return;
            }
            packet = byteBuf.readInt();
            size = byteBuf.readInt();
        }
        Packet p = PacketRegistry.generate(packet, size, byteBuf);
        if (p == null) return;
        list.add(p);
        packet = -1;
        size = -1;
    }
}
