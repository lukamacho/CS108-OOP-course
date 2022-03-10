package Controller;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class shoppingServlet extends HttpServlet {
    public shoppingServlet(){
        super();
    }
    @Override
    protected  void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/storeHomepage.jsp");
        requestDispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session curSession = (Session)req.getSession().getAttribute("session");
        curSession.updateList(req);
        BigDecimal totalSum = curSession.totalSum();
        req.setAttribute("totalSum",totalSum);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/cartList.jsp");
        requestDispatcher.forward(req,resp);

    }

}

