package qunar.tc.qmq.backup.container;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import qunar.tc.qmq.backup.api.DeadMessageApiServlet;
import qunar.tc.qmq.backup.api.MessageApiServlet;
import qunar.tc.qmq.backup.api.MessageDetailsServlet;
import qunar.tc.qmq.backup.api.MessageRecordsServlet;
import qunar.tc.qmq.backup.service.MessageService;
import qunar.tc.qmq.backup.startup.ServerWrapper;
import qunar.tc.qmq.configuration.DynamicConfig;
import qunar.tc.qmq.configuration.DynamicConfigLoader;

import javax.servlet.Servlet;

public class Bootstrap {
    public static void main(String[] args) throws Exception {
        DynamicConfig config = DynamicConfigLoader.load("backup.properties");
        ServerWrapper wrapper = new ServerWrapper(config);
        Runtime.getRuntime().addShutdownHook(new Thread(wrapper::destroy));
        wrapper.start();

        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.setResourceBase(System.getProperty("java.io.tmpdir"));

        final MessageService messageService = wrapper.getMessageService();

        DeadMessageApiServlet deadMessageApiServlet = new DeadMessageApiServlet(messageService);
        addServlet(context, deadMessageApiServlet, "/api/message/dead");

        MessageApiServlet messageApiServlet = new MessageApiServlet(messageService);
        addServlet(context, messageApiServlet, "/api/message");

        MessageDetailsServlet messageDetailsServlet = new MessageDetailsServlet(messageService);
        addServlet(context, messageDetailsServlet, "/api/message/detail");

        MessageRecordsServlet messageRecordsServlet = new MessageRecordsServlet(messageService);
        addServlet(context, messageRecordsServlet, "/api/message/records");

        int port = config.getInt("backup.server.http.port", 8080);
        final Server server = new Server(port);
        server.setHandler(context);
        server.start();
        server.join();
    }

    private static void addServlet(final ServletContextHandler context, Servlet servlet, String pathSpec) {
        ServletHolder deadMessageServletHolder = new ServletHolder(servlet);
        deadMessageServletHolder.setAsyncSupported(true);
        context.addServlet(deadMessageServletHolder, pathSpec);
    }

}
