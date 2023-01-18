package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbService {

    private static DbService instance;

    public static DbService getInstance(){
        if(DbService.instance == null){
            DbService.instance = new DbService();
        }
        return DbService.instance;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/simpledatastore", "postgres", "1234");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
