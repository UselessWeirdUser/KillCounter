package it.feargames.killcounter.playerstats;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerStatsExpansion extends PlaceholderExpansion {
    private final PlayerStatsManager mem;

    public PlayerStatsExpansion(PlayerStatsManager mem) {
        this.mem = mem;
    }

    @Override
    public @NotNull String getAuthor() {
        return "UselessWeirdUser";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "killcounter";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player != null) {
            if (params.equalsIgnoreCase("kills")) {
                return mem.getStats(player).getKills().toString();
            } else if (params.equalsIgnoreCase("deaths")) {
                return mem.getStats(player).getDeaths().toString();
            } else if (params.equalsIgnoreCase("killstreak")) {
                return mem.getStats(player).getKillStreak().toString();
            }
        }
        return null;
    }
}
