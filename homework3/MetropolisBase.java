import java.sql.*;
import java.util.*;

public class MetropolisBase {

    //Some private variables ;
    private String baseName, tableName;
    private Connection conn;
    private String serverName = "127.0.0.1";
    private String databaseName ="example";

    public MetropolisBase(String baseName, String tableName) throws SQLException, ClassNotFoundException {
        this.baseName = baseName;
        this.tableName = tableName;
        makeNewBaseTable();
    }

    /**
     * Composition of methods which creates the whole base.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void makeNewBaseTable() throws SQLException, ClassNotFoundException {
        createConnection();
        createDatabase();
        createEmptyTable();
    }

    /**
     * Function to create connection.
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private void createConnection() throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = ConnectionManager.getInstance(serverName,databaseName);

    }

    /**
     * Function to create empty table with sql script.
     * @throws SQLException
     */
    private void createEmptyTable() throws SQLException {
        Statement st = conn.createStatement();
        st.execute("Drop table if exists " + tableName + " ;");
        st.execute("Create table " + tableName + "(metropolis char(64),continent varchar(64),population BIGINT);");
        st.close();
    }

    /**
     * Function to create database with sql script.
     * @throws SQLException
     */
    private void createDatabase() throws SQLException {
            Statement st = conn.createStatement();
            st.execute("Drop database if exists " + baseName + ";");
            st.execute("Create database " + baseName + ";");
            st.execute("Use " + baseName + ";");
            st.close();
    }
    /**
     * @param metropolis
     * @param continent
     * @param population
     * Metropolis addition procedure.
     */
   public void addNewMetropolis(String metropolis,String continent,String population){
        try {
            PreparedStatement ps = conn.prepareStatement("Insert into " + tableName + " values (?, ?, ?);");
            ps.setString(1, metropolis);
            ps.setString(2, continent);
            ps.setString(3, population);
            ps.execute();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
   }

    /**
     *
     * @param metropolis
     * @param continent
     * @param population
     * @param popFilter
     * @param matchFilter
     * @return
     * This function generates sql script and returns it.
     */
   private String searchScript(String metropolis,String continent, String population, boolean popFilter,boolean matchFilter){
        StringBuilder st = new StringBuilder("Select * from " + tableName);
        if(!metropolis.equals("")){
            if(matchFilter){
                st.append(" Where metropolis = '");
                st.append(metropolis+ "'");
            }else{
                st.append(" Where metropolis like '%");
                st.append(metropolis+"%'");
            }
        }if(!population.equals("")){
            if(!metropolis.equals("")){
                st.append(" and population ");
            }else{
                st.append(" where population ");
            }
            if(popFilter){
                st.append(" > " + population);
            }else{
                st.append(" < " + population);
            }
       }if(!continent.equals("")){
            if(!population.equals("") || !metropolis.equals("")){
                st.append(" and continent");
            }else{
                st.append(" where continent");
            }
            if(matchFilter){
                st.append(" = '"+continent+"'");
            }else{
                st.append(" like '%"+ continent+"%'");
            }
       }
        st.append(";");
        return st.toString();
   }

    /**
     *
     * @param metropolis
     * @param continent
     * @param population
     * @param popFilter
     * @param matchFilter
     * @return
     * @throws SQLException
     * This function returns the arraylist of arrraylist strings which are the metropolises
     * and its features which have appropriate charasterization.
     */
    public ArrayList<ArrayList<String>> searchMetropolises(String metropolis,String continent, String population, boolean popFilter,boolean matchFilter) throws SQLException {
        ArrayList<ArrayList<String>> answer = new ArrayList<>();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(searchScript(metropolis,continent,population,popFilter,matchFilter));
        while (rs.next()) {
            ArrayList<String> cur = new ArrayList<>();
            cur.add(rs.getString(1));
            cur.add(rs.getString(2));
            cur.add(rs.getString(3));
            answer.add(cur);
        }
        return answer;
    }
}
