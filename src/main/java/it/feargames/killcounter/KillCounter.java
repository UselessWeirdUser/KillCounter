package it.feargames.killcounter;

import it.feargames.killcounter.playerstats.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Objects;

public final class KillCounter extends JavaPlugin {
    private PlayerStatsDatabase db;
    private PlayerStatsManager mem;

    @Override
    public void onEnable() {
        this.db = new PlayerStatsDatabase();
        this.mem = new PlayerStatsManager();
        getServer().getPluginManager().registerEvents(new PlayerStatsListener(this.db, this.mem), this);
        Objects.requireNonNull(this.getCommand("stats")).setExecutor(new PlayerStatsCommand(this.mem));

        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlayerStatsExpansion(this.mem).register();
        }
    }

    @Override
    public void onDisable() {
        for (Player player : getServer().getOnlinePlayers()) {
            PlayerStatsModel stats = mem.popStats(player);
            try {
                db.editRecord(stats);
                db.closeConnections();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
