package me.partlysunny.game;

import io.netty.channel.ChannelHandlerContext;
import me.partlysunny.TUtil;
import me.partlysunny.game.map.GameMap;
import me.partlysunny.game.unit.type.UnknownCitizen;
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
                TUtil.debug("Refreshing " + ctx.channel().remoteAddress());
                ctx.writeAndFlush(new ClientboundMapUpdate(gameState));
            }
            TUtil.debug("Refreshed all connections");
        } else {
            if (connections.containsKey(player)) {
                TUtil.debug("Specifically refreshing " + connections.get(player).channel().remoteAddress());
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
        TUtil.debug("Generating map...");
        gameState.gameMap = new GameMap(70, 70, true);
        TUtil.debug("Generated map!");
        // drop the players!
        int quarterWidth = gameState.gameMap.getWidth() / 4;
        int quarterHeight = gameState.gameMap.getHeight() / 4;
        int i = 0;
        for (int connection : connections.keySet()) {
            int x = quarterWidth;
            int y = quarterHeight;
            if (i == 1) {
                x = gameState.gameMap.getWidth() - quarterWidth;
            } else if (i == 2) {
                x = gameState.gameMap.getHeight() - quarterHeight;
                y = gameState.gameMap.getHeight() - quarterHeight;
            } else if (i == 3) {
                y = gameState.gameMap.getHeight() - quarterHeight;
            }

            TUtil.debug("Spawning player " + connection + " at x: " + x + " y: " + y);
            boolean b = gameState.gameMap.summon(new UnknownCitizen(gameState.gameMap).setOwnerID(connection), x, y);
            while (!b) {
                TUtil.debug("Trying again to spawn player " + connection + " at x: " + x + " y: " + y);
                b = gameState.gameMap.summon(new UnknownCitizen(gameState.gameMap).setOwnerID(connection), x, y);
            }
            i++;
        }
        TUtil.debug("Successfully spawned " + connections.size() + " players");
        refresh(-1);
        currentRound = 1;
        currentTurn = 0;
    }

}
