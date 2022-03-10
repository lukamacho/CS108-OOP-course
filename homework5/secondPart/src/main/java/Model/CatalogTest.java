package Model;

import junit.framework.TestCase;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class CatalogTest extends TestCase {
    public void  testFirst() throws SQLException, ClassNotFoundException {
        Catalog catalog = new Catalog();
        assertEquals(14,catalog.productCatalog().size());
        Product cur = Catalog.findProduct("HC");
        BigDecimal price = cur.getPrice();
        Product product = new Product("HC","Classic Hoodie","Hoodie.jpg",price);
       assertEquals(true,cur.equals(product));
       Connection connection = Connector.getInstance();
       connection.close();
    }

}
