import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static Connection conn;

    public static Connection getInstance(String serverName, String databaseName) throws SQLException {
        if(conn == null){
           conn = DriverManager.getConnection("jdbc:mysql://"+serverName+"/"+databaseName,
                    "luka2001","luka2001");
        }
        return conn;
    }
}
