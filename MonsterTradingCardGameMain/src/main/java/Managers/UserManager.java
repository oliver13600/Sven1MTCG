package Managers;

import Database.DbService;
import Game.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {

    private static UserManager single_instance = null;

    public static UserManager getInstance() {
        if (single_instance == null) {
            single_instance = new UserManager();
        }
        return single_instance;
    }

    public User authorizeUser(String token) {
        try {
            Connection conn = DbService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT username, name, bio, image, coins, games, wins, elo FROM users WHERE token = ? AND logged = TRUE;");
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            ps.close();
            if (!rs.next()) {
                rs.close();
                conn.close();
                return null;
            }
            User user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8));
            rs.close();
            conn.close();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isAdmin(String token) {
        try {
            Connection conn = DbService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(username) FROM users WHERE token = ? AND admin = TRUE AND logged = TRUE;");
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            ps.close();
            if (!rs.next() || rs.getInt(1) != 1) {
                rs.close();
                conn.close();
                return false;
            }
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean registerUser(String username, String pwd) {
        String token = "Basic " + username + "-mtcgToken";
        try {
            Connection conn = DbService.getInstance().getConnection();
            PreparedStatement ps;
            ps = conn.prepareStatement("SELECT COUNT(username) FROM users WHERE username = ?;");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            ps.close();
            if (!rs.next() || rs.getInt(1) > 0) {
                return false;
            }
            if (username.equals("admin")) {
                ps = conn.prepareStatement("INSERT INTO users(username, pwd, token, admin) VALUES(?,?,?,TRUE);");
            } else {
                ps = conn.prepareStatement("INSERT INTO users(username, pwd, token) VALUES(?,?,?);");
            }
            ps.setString(1, username);
            ps.setString(2, pwd);
            ps.setString(3, token);
            int affectedRows = ps.executeUpdate();
            ps.close();
            conn.close();
            return affectedRows != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean loginUser(String username, String pwd) {
        try {
            Connection conn = DbService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET logged = TRUE WHERE username = ? AND pwd = ?;");
            ps.setString(1, username);
            ps.setString(2, pwd);
            int affectedRows = ps.executeUpdate();
            ps.close();
            conn.close();
            if (affectedRows == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean logoutUser(String username, String pwd) {
        try {
            Connection conn = DbService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET logged = FALSE WHERE username = ? AND pwd = ?;");
            ps.setString(1, username);
            ps.setString(2, pwd);
            int affectedRows = ps.executeUpdate();
            ps.close();
            conn.close();
            if (affectedRows == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
