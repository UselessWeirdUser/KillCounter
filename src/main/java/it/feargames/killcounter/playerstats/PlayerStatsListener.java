package it.feargames.killcounter.playerstats;

import it.feargames.killcounter.KillCounter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class PlayerStatsListener implements Listener {
    private final PlayerStatsDatabase db;
    private final PlayerStatsManager mem;

    public PlayerStatsListener(PlayerStatsDatabase db, PlayerStatsManager mem) {
        this.db = db;
        this.mem = mem;
    }

    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent e) {
        try {
            PlayerStatsModel stats = db.readRecord(e.getUniqueId());
            mem.setStats(stats);
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
        mem.setStats(ps);

        if (killer != null) {
            PlayerStatsModel ks = mem.getStats(killer);
            ks.setKillStreak(ks.getKillStreak() + 1);
            ks.setKills(ks.getKills() + 1);
            mem.setStats(ks);
        }
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent e) {
        JavaPlugin plugin = JavaPlugin.getPlugin(KillCounter.class);
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                db.editRecord(mem.popStats(e.getPlayer()));
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        });
    }
}
