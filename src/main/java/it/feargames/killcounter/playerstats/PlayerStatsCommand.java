package it.feargames.killcounter.playerstats;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerStatsCommand implements CommandExecutor {
    private final PlayerStatsManager mem;

    public PlayerStatsCommand(PlayerStatsManager mem) {
        this.mem = mem;
    }
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length != 0) {
            return false;
        }

        if (sender instanceof Player player) {
            PlayerStatsModel stats = mem.getStats(player);

            player.sendMessage("Your current kill streak is: " + stats.getKillStreak());
            if (stats.getDeaths() != 1) {
                player.sendMessage("You died " + stats.getDeaths() + " times");
            } else {
                player.sendMessage("You died " + stats.getDeaths() + " time");
            }
            if (stats.getKills() != 1) {
                player.sendMessage("You killed " + stats.getKills() + " players");
            } else {
                player.sendMessage("You killed " + stats.getKills() + " player");
            }
        }

        return true;
    }
}
