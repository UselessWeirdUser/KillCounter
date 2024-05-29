package it.feargames.killcounter.playerstats;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class PlayerStatsListener implements Listener {
    private final PlayerStatsDatabase db;
    private final PlayerStatsManager mem;

    public PlayerStatsListener(PlayerStatsDatabase db, PlayerStatsManager mem) {
        this.db = db;
        this.mem = mem;
    }

    @EventHandler
    public void onPlayerJoin(AsyncPlayerPreLoginEvent e) { // TODO: make this async
        try {
            PlayerStatsModel stats = db.readRecord(e.getUniqueId());
            mem.insertStats(stats);
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity().getPlayer();
        assert player != null;
        Player killer = player.getKiller();
        PlayerStatsModel ps = mem.getStats(player);
        ps.setKillStreak(0);
        ps.setDeaths(ps.getDeaths() + 1);
        mem.editStats(ps);

        if (killer != null) {
            PlayerStatsModel ks = mem.getStats(killer);
            ks.setKillStreak(ks.getKillStreak() + 1);
            ks.setKills(ks.getKills() + 1);
            mem.editStats(ks);
        }
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent e) { // TODO: make this async
        try {
            db.editRecord(mem.popStats(e.getPlayer()));
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }
}
