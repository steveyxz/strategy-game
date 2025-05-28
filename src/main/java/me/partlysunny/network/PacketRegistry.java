package me.partlysunny.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.partlysunny.network.client.ServerboundIdRequest;
import me.partlysunny.network.server.ClientboundIdResponse;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class PacketRegistry {

    private static final Map<Integer, Class<? extends Packet>> packets = new HashMap<>();
    private static final Map<Class<? extends Packet>, Integer> packetsByClass = new HashMap<>();

    private static final int SERVERBOUND_MASK = 1 << 31;

    static {
        packets.put(SERVERBOUND_MASK ^ 256, ServerboundIdRequest.class);
        packets.put(256, ClientboundIdResponse.class);
        for (Integer i : packets.keySet()) {
            packetsByClass.put(packets.get(i), i);
        }
    }

    public static ByteBuf buildPacket(Packet packet) {
        // find class by value
        if (!packetsByClass.containsKey(packet.getClass())) {
            throw new IllegalStateException(packet.getClass().getSimpleName() + " is not registered!");
        }
        Integer i = packetsByClass.get(packet.getClass());
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(i);
        packet.serialise(buf);
        return buf;
    }

    public static Packet generate(int type, ByteBuf data) {
        if (packets.containsKey(type)) {
            Class<? extends Packet> clazz = packets.get(type);
            Packet packet = null;
            try {
                packet = clazz.getDeclaredConstructor().newInstance();
                if (data.readableBytes() >= packet.payloadSize()) {
                    packet.load(data);
                    return packet;
                } else {
                    return null;
                }
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        throw new IllegalStateException("Unknown packet type: " + type);
    }

}
