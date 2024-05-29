package it.feargames.killcounter.playerstats;

import org.bukkit.entity.Player;

public class PlayerStatsManager {
    public PlayerStatsModel getStats(Player p) {
        // TODO: get stats from the memory
        return null;
    }

    public void editStats(Player p, PlayerStatsModel s) {
        // TODO: edit stats in-memory, if they exist. otherwise, add a new entry
    }

    public PlayerStatsModel popStats(Player p) {
        // TODO: get the stats for player p, then delete them
        return null;
    }
}
