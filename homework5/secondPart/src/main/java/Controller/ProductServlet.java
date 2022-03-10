package Controller;

import Model.Catalog;
import Model.Product;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;


public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = (Session)req.getSession().getAttribute("session");
        String  ToAdd = (String)req.getParameter("productID");
        Product productToAdd = null;
        try {
            productToAdd = Catalog.findProduct(ToAdd);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        session.addToList(productToAdd);
        Session mysession = (Session)req.getSession().getAttribute("session");
        BigDecimal totalCost = mysession.totalSum();
        req.setAttribute("totalCost", totalCost);
        req.getRequestDispatcher("/cartList.jsp").forward(req, resp);

    }
    @Override
    protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        Session session = (Session)req.getSession().getAttribute("session");
        session.updateList(req);
        Session mysession = (Session)req.getSession().getAttribute("session");
        BigDecimal totalCost = mysession.totalSum();
        req.setAttribute("totalCost", totalCost);
        req.getRequestDispatcher("/cartList.jsp").forward(req, resp);
    }
}
