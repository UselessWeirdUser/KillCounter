package it.feargames.killcounter.playerstats;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerStatsManager {
    private final Map<String, PlayerStatsModel> data = new ConcurrentHashMap<>();

    public PlayerStatsModel getStats(Player p) {
        return data.get(p.getUniqueId().toString());
    }

    public void setStats(PlayerStatsModel s) {
        data.put(s.getPlayerUUID(), s);
    }

    public PlayerStatsModel popStats(Player p) {
        PlayerStatsModel stats = getStats(p);
        data.remove(p.getUniqueId().toString());
        return stats;
    }
}
