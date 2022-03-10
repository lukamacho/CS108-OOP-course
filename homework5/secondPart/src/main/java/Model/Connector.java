package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connector {
    private static Connection con;
    private final static String userName = "root";
    private final static String password = "root123";
    private final static String serverName = "localhost";
    private final static String baseName = "StoreBase";
    public final static String tableName = "products";

    public static Connection getInstance() throws ClassNotFoundException, SQLException {
        if (con == null) {
            synchronized (Connector.class) {
                if(con == null) {
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://", userName, password);
                    Statement st = con.createStatement();
                    st.execute("Use " + baseName );
                }
            }
        }
        return con;
    }
    public static void close() throws SQLException {
        if(con != null){
            con.close();
        }
    }
}
