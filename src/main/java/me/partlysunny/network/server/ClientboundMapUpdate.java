package me.partlysunny.network.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import me.partlysunny.game.GameState;
import me.partlysunny.network.Packet;
import me.partlysunny.network.client.ClientLoop;

public class ClientboundMapUpdate implements Packet {

    private GameState state;

    public ClientboundMapUpdate() {
    }

    public ClientboundMapUpdate(GameState state) {
        this.state = state;
    }


    @Override
    public void receive(ChannelHandlerContext ctx) {
        ClientLoop.state = state;
        ClientLoop.refresh();
    }

    @Override
    public void load(ByteBuf buf) {
        state = new GameState();
        state.load(buf);
    }

    @Override
    public ByteBuf serialise(ByteBuf base) {
        return state.serialise(base);
    }
}
