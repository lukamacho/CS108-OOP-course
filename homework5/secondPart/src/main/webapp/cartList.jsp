<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.math.*, java.lang.String,Controller.Session, java.util.*,Model.Product,Model.Catalog,Model.Connector" %>
<html>
<head>
    <title>Shopping Cart</title>
</head>
<body>
        <h1>Shoping Cart </h1>
            <%
            Session cur= (Session)request.getSession().getAttribute("session");
            Map<Product,Integer> all = cur.getTable();
            Double sum = 0.0;
            %>
            <form action="/product" method="post">
            <ul>
            <%
            for(Product product : all.keySet()){
                 int num = all.get(product);
                 BigDecimal yvela= new BigDecimal(num);
                 String productid = product.getid();
                 sum+=(product.getPrice().doubleValue()*num);
                 out.println(
                 "<li>"+
                        "<p>"+
                            "<input type = \"text\" value=" + num + " name=" +productid+ " />"+
                            " " + product.getName() + " " + product.getPrice()+
                        "</p>"+
                 "</li>"
                 );
           }%>
        </ul>


             Total: <%=sum%> <input type="submit" value="UpdateCart">
        </form>
        <a href="storeHomepage.jsp"> Continue Shopping</a>
</body>
</html>