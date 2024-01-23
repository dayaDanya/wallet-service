package org.ylab.infrastructure.input;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class MigrationExecutor implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        MigrationConfig config = new MigrationConfig();
        try {
            config.migrate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
