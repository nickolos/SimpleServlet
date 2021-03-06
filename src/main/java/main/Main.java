package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import servlets.MirrorRequestServlet;

public class Main {
    public static void main(String[] args) throws Exception {

        MirrorRequestServlet mirrorRequestServlet = new MirrorRequestServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(mirrorRequestServlet), "/*");
        int port;
        try {
             port = Integer.parseInt(System.getenv("PORT"));
        }
        catch (NumberFormatException e){
           port = 8080;
        }
        Server server = new Server(port);
        server.setHandler(context);
        server.start();
        server.join();
    }
}