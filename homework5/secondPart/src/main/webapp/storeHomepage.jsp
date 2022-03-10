<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Model.Catalog, java.sql.* ,java.util.*,Model.Product"%>
<!DOCTYPE html>
<html>
     <head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <title> Student store  </title>

     </head>
     <body>
     <h1>Student store</h1>
     <p>Items available: </p>
        <ul>
          <%
            Catalog catalog = (Catalog)request.getServletContext().getAttribute("catalog");
            ArrayList<Product> all = catalog.productCatalog();
            for(int i=0;i<all.size();i++){
            out.println("<li><a href = \"/productPage.jsp?id=" + all.get(i).getid() + "\">" + all.get(i).getName() + "</a></li>");
            }
          %>
         </ul>
     </body>
 </html>
