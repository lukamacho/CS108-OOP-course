package servlets;

import javax.enterprise.inject.New;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class NewAccountServlet extends HttpServlet {
    public NewAccountServlet(){
        super();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
      RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/createAccount.jsp");
      requestDispatcher.forward(req,resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountManager accountManager =(AccountManager) req.getServletContext().getAttribute("Account Manager");
        if(!accountManager.exists(req.getParameter("name"))){
            accountManager.createNewAccount(req.getParameter("name"),req.getParameter("password"));
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/welcome.jsp");
            requestDispatcher.forward(req,resp);
        }else{
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/ExistedUser.jsp");
            requestDispatcher.forward(req,resp);
        }
    }
}
