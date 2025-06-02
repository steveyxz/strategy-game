package me.partlysunny.game;

import io.netty.channel.ChannelHandlerContext;
import me.partlysunny.network.server.ClientboundMapUpdate;

import java.util.HashMap;
import java.util.Map;

public class GameHandler {

    public static GameState gameState;
    public static Map<Integer, ChannelHandlerContext> connections = new HashMap<>();

    public static int currentRound = -1;
    public static int currentTurn = 0;

    public static int playerCount = 0;

    // -1 if to all players
    public static void refresh(Integer player) {
        if (player == -1) {
            for (ChannelHandlerContext ctx : connections.values()) {
                ctx.writeAndFlush(new ClientboundMapUpdate(gameState));
            }
        } else {
            if (connections.containsKey(player)) {
                connections.get(player).writeAndFlush(new ClientboundMapUpdate(gameState));
            }
        }
    }

    public static boolean addPlayer(Integer id, ChannelHandlerContext ctx) {
        if (playerCount > 4) return false;
        playerCount++;
        connections.put(id, ctx);
        return true;
    }

    public static void startGame() {
        gameState = new GameState();
        gameState.gameMap = new GameMap(60, 60, true);
        refresh(-1);
        currentRound = 1;
        currentTurn = 0;
    }

}
