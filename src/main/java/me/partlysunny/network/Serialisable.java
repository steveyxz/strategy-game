package me.partlysunny.network;

import io.netty.buffer.ByteBuf;

public interface Serialisable {

    /** load will be what happens when we receive this packet. will will load a buf into the object
     * @param buf the data to load into this object
     */
    void load(ByteBuf buf);

    /** serialise converts the data int his object into a bytebuf
     * @param base the current state of the buffer
     * @return updated state with this object's contents
     */
    ByteBuf serialise(ByteBuf base);

}
