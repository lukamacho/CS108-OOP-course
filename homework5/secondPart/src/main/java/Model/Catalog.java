package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Catalog {

    public Catalog() {
        try {
            Connection connector = Connector.getInstance(); } catch (ClassNotFoundException e) { e.printStackTrace(); } catch (SQLException throwables) { throwables.printStackTrace(); }
    }
    public ArrayList<Product> productCatalog() throws SQLException {
        ArrayList<Product> result = new ArrayList<>();
        ResultSet resultSet = null;
        Connection connection = null;
        try { connection = Connector.getInstance(); } catch (ClassNotFoundException e) { e.printStackTrace(); }
        Statement statement = connection.createStatement();
        String sqlScript = "SELECT * from products";
        try {
            resultSet  = statement.executeQuery(sqlScript);
            while(resultSet.next()){
                Product cur = new Product(resultSet.getString("productid"),resultSet.getString("name"),
                        resultSet.getString("imagefile"),resultSet.getBigDecimal("price"));
                result.add(cur);
            }
        } catch (SQLException throwables) { throwables.printStackTrace(); }
        return result;
    }

    public static Product findProduct(String productId) throws SQLException {
        Product curProduct = null;
        StringBuilder sqlScript =new StringBuilder("Select * from "+ Connector.tableName +" where productid = ");
        sqlScript.append("'").append(productId).append("'").append(" ;");
        //System.out.println(sqlScript);
        Connection connection = null;
        try {
            connection = Connector.getInstance();
        } catch (ClassNotFoundException e) { e.printStackTrace(); }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlScript.toString());
        if(resultSet.next()){
            curProduct = new Product(resultSet.getString("productId"),resultSet.getString("name"),
                    resultSet.getString("imagefile"),resultSet.getBigDecimal("price"));
        }
        return curProduct;
    }


}
