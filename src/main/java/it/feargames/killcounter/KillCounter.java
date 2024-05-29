package it.feargames.killcounter;

import it.feargames.killcounter.playerstats.PlayerStatsDatabase;
import it.feargames.killcounter.playerstats.PlayerStatsExpansion;
import it.feargames.killcounter.playerstats.PlayerStatsListener;
import it.feargames.killcounter.playerstats.PlayerStatsManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class KillCounter extends JavaPlugin {

    @Override
    public void onEnable() {
        PlayerStatsDatabase db = new PlayerStatsDatabase();
        PlayerStatsManager mem = new PlayerStatsManager();
        getServer().getPluginManager().registerEvents(new PlayerStatsListener(db, mem), this);

        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlayerStatsExpansion(mem).register();
        }
    }

    @Override
    public void onDisable() {
        // TODO: save everything to db
    }
}
