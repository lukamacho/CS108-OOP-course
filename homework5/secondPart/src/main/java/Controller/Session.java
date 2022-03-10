package Controller;

import Model.Product;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Session {
    private Map<Product, Integer> shoppingTable;
    private BigDecimal totalSum;
    private HttpSession httpSession;
    public Session(HttpSession httpSession) {
        shoppingTable = new HashMap<>();
        totalSum = new BigDecimal(0);
        this.httpSession = httpSession;
    }
    public Map<Product, Integer> getTable(){
        return shoppingTable;
    }
    public void addToList(Product product){
        if(!shoppingTable.containsKey(product)){
            shoppingTable.put(product,1);
        }else{
            shoppingTable.put(product,shoppingTable.get(product)+1);
        }
    }
    public BigDecimal totalSum(){
        BigDecimal result = new BigDecimal(0);

        return  result;
    }
    public void updateList(HttpServletRequest request){
        Map<Product, Integer> newTable = new HashMap<>();
        Map<String,String[]> es = request.getParameterMap();
        System.out.println(es.size());
        for(String st : es.keySet()){
            System.out.println(st);
        }
        for(Product product: shoppingTable.keySet()) {
            int count = shoppingTable.get(product);
            try {
                count = Integer.parseInt(es.get(product.getid())[0]);
            } catch (NumberFormatException ignored) {
                System.out.println("illegal number format");
            }

            if (count > 0) {
                newTable.put(product, count);
            }
        }
        shoppingTable = newTable;
    }

}
