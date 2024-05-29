package it.feargames.killcounter.playerstats;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.UUID;

public class PlayerStatsDatabase {
    private HikariDataSource dataSource;
    private final String url;
    private final String user;
    private final String password;

    public PlayerStatsDatabase(String host, Integer port, String dbName, String user, String password) {
        this.url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
        this.user = user;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(10);

        if (this.dataSource == null) {
            this.dataSource = new HikariDataSource(config);
            try (Connection conn = this.dataSource.getConnection()) {
                try (PreparedStatement statement = conn.prepareStatement("CREATE TABLE IF NOT EXISTS stats (uuid varchar(36) primary key, kills int, deaths int, killstreak int)")) {
                    statement.execute();
                }
            }
        }

        return this.dataSource.getConnection();
    }

    public void editRecord(PlayerStatsModel r) throws SQLException {
        try (PreparedStatement statement = getConnection().prepareStatement("UPDATE stats SET kills = ?, deaths = ?, killstreak = ? WHERE uuid = ?")) {
            statement.setInt(1, r.getKills());
            statement.setInt(2, r.getDeaths());
            statement.setInt(3, r.getKillStreak());
            statement.setString(4, r.getPlayerUUID());
            statement.executeUpdate();
        }
    }

    public PlayerStatsModel readRecord(UUID uuid) throws SQLException {
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM stats WHERE uuid = ?")) {
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            PlayerStatsModel stats;
            if (resultSet.next()) {
                stats = new PlayerStatsModel(resultSet.getString("uuid"), resultSet.getInt("kills"), resultSet.getInt("deaths"), resultSet.getInt("killstreak"));
                statement.close();
                return stats;
            }
        }

        try (PreparedStatement statement = getConnection().prepareStatement("INSERT INTO stats(uuid, kills, deaths, killstreak) VALUES (?, ?, ?, ?)")) {
            statement.setString(1, uuid.toString());
            statement.setInt(2, 0);
            statement.setInt(3, 0);
            statement.setInt(4, 0);
            statement.executeUpdate();
            return new PlayerStatsModel(uuid.toString(), 0, 0, 0);
        }
    }

    public void closeConnections() throws SQLException {
        this.dataSource.close();
    }
}
