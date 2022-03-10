
<%@ page import="Model.Catalog, java.sql.* ,java.util.*,Model.Product"%>
<html>
<head>
    <%
        Catalog catalog = (Catalog)request.getServletContext().getAttribute("catalog");
        Product p = catalog.findProduct((String)request.getParameter("id"));
    %>
    <title> <%=p.getName()%></title>
</head>
<body>
      <h1> <%=p.getName()%> </h1>
      <img src = <%= p.getImageUrl() %> alt = "not found" >
      <form action="/product" method="get">
            <input name="productID" type="hidden" value="<%=p.getid()%>"/>
            <%=p.getPrice()%> <input type="submit" value="Add to Cart">

       </form>
</body>
</html>