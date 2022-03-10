package Model;

import junit.framework.TestCase;

import java.math.BigDecimal;

public class ProductTest extends TestCase {


    public void testFirst(){
        Product product = new Product("HC","hoodie","es",new BigDecimal(2));
        assertEquals("HC",product.getid());
        assertEquals("hoodie",product.getName());
        assertEquals("store-images/es",product.getImageUrl());
        assertEquals(new BigDecimal(2),product.getPrice());
    }
    public void testComplete(){
        Product product = new Product("HR","hoodie","image",new BigDecimal(10));
        assertEquals("HR",product.getid());
        assertEquals("hoodie",product.getName());
        assertEquals("store-images/image",product.getImageUrl());
        assertEquals(new BigDecimal(10),product.getPrice());
        Product product1 =null;
        assertEquals(false,product.equals(product1));
        Product product2 = new Product("HR","hoodie","image",new BigDecimal(10));
        assertEquals(true,product.equals(product2));
        assertEquals(false,product.hashCode()==-1);
    }
}
