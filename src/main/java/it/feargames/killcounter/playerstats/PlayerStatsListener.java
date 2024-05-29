package it.feargames.killcounter.playerstats;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerStatsListener implements Listener {
    private final PlayerStatsDatabase db;
    private final PlayerStatsManager mem;

    public PlayerStatsListener(PlayerStatsDatabase db, PlayerStatsManager mem) {
        this.db = db;
        this.mem = mem;
    }

    @EventHandler
    public void onPlayerJoin(AsyncPlayerPreLoginEvent e) {
        // TODO: read data from db
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        // TODO: update kills / deaths / kill streak for the players
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent e) {
        // TODO: save data to db
    }
}
