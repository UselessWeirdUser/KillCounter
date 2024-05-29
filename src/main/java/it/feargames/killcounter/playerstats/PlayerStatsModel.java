package it.feargames.killcounter.playerstats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PlayerStatsModel {
    private String playerUUID;
    private Integer kills;
    private Integer deaths;
    private Integer killStreak;
}
