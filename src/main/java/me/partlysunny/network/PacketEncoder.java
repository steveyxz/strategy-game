package me.partlysunny.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.partlysunny.TUtil;

public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        TUtil.debug("Encoding a packet of type " + packet.getClass().getName());
        ByteBuf newData = PacketRegistry.buildPacket(packet);
        byteBuf.writeBytes(newData);
        TUtil.debug("Successfully encoded the packet -- sending over to " + channelHandlerContext.channel().remoteAddress());
        newData.release();
    }
}
