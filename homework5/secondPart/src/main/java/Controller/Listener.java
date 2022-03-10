package Controller;

import Model.Catalog;
import Model.Connector;
import sun.misc.Cache;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public class Listener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        Catalog catalog = new Catalog();
        servletContext.setAttribute("catalog",catalog);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            Connector.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
