package Game;

import Database.DbService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class User {

    private final String username;
    private final String name;
    private final String bio;
    private final String image;
    private final Integer coins;
    private int games;
    private int wins;
    private float elo;

    public User(String username, String name, String bio, String image, int coins, int games, int wins, float elo) {
        this.username = username;
        this.name = name;
        this.bio = bio;
        this.image = image;
        this.coins = coins;
        this.games = games;
        this.wins = wins;
        this.elo = elo;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Integer getCoins() {
        return coins;
    }

    public String getBio() {
        return bio;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public float getElo() {
        return elo;
    }

    public void setElo(float elo) {
        this.elo = elo;
    }

    public String getInfo() {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("Name:", name);
            map.put("Bio:", bio);
            map.put("Image:", image);
            map.put("Coins:", coins.toString());
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getStats() {
        try {
            Map<String, Integer> map = new HashMap<>();
            map.put("Wins:", wins);
            map.put("Games:", games);
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean buyPackage() {
        try {
            if (coins < 5) {
                return false;
            }
            Connection conn = DbService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET coins = ? WHERE username = ?;");
            ps.setInt(1, coins - 5);
            ps.setString(2, username);
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean battleWon() {
        wins++;
        games++;
        elo += 3;
        return saveStats();
    }

    public boolean battleLost() {
        games++;
        elo -= 5;
        return saveStats();
    }

    public boolean battleDraw() {
        games++;
        return saveStats();
    }

    public boolean saveStats() {
        try {
            Connection conn = DbService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET wins = ?, games = ?, elo = ? WHERE username = ?;");
            ps.setInt(1, wins);
            ps.setInt(2, games);
            ps.setFloat(3, elo);
            ps.setString(4, username);
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setUserInfo(String name, String bio, String image) {
        try {
            Connection conn = DbService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET name = ?, bio = ?, image = ? WHERE username = ?;");
            ps.setString(1, name);
            ps.setString(2, bio);
            ps.setString(3, image);
            ps.setString(4, username);
            int affectedRows = ps.executeUpdate();
            ps.close();
            conn.close();
            if (affectedRows == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}