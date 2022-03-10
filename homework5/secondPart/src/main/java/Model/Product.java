package Model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    String name;
    String imageUrl;
    BigDecimal price;
    String id;
    public Product(String id, String name,String imageUrl,BigDecimal price){
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.id = id;
    }
    public BigDecimal getPrice(){
        return price;
    }
    public String getName(){
        return  name;
    }
    public String getid(){
        return id;
    }
    public String getImageUrl(){
        return "store-images/"+imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(imageUrl, product.imageUrl) && Objects.equals(price, product.price) && Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, imageUrl, price, id);
    }
}
