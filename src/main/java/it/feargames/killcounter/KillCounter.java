package it.feargames.killcounter;

import it.feargames.killcounter.playerstats.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Objects;

public final class KillCounter extends JavaPlugin {
    private PlayerStatsDatabase db;
    private PlayerStatsManager mem;

    @Override
    public void onEnable() {
        FileConfiguration config = this.getConfig();
        config.addDefault("database-server", "localhost");
        config.addDefault("database-port", 3306);
        config.addDefault("database-name", "database");
        config.addDefault("database-user", "root");
        config.addDefault("database-password", "password");
        config.options().copyDefaults(true);
        saveConfig();
        this.db = new PlayerStatsDatabase(config.getString("database-server"),
                config.getInt("database-port"), config.getString("database-name"),
                config.getString("database-user"), config.getString("database-password"));
        this.mem = new PlayerStatsManager();
        getServer().getPluginManager().registerEvents(new PlayerStatsListener(this.db, this.mem), this);
        Objects.requireNonNull(this.getCommand("stats")).setExecutor(new PlayerStatsCommand(this.mem));

        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlayerStatsExpansion(this.mem).register();
        }
    }

    @Override
    public void onDisable() {
        try {
            for (Player player : getServer().getOnlinePlayers()) {
                PlayerStatsModel stats = mem.popStats(player);
                db.editRecord(stats);
            }
            db.closeConnections();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
