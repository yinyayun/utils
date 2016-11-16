/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.xml.XmlConfiguration;

/**
 * JettyServerDemo.java
 *
 * @author yinyayun
 */
public class JettyServerDemo {
    public static void main(String[] args) throws Exception {
        String jettyHome = args[0];
        System.setProperty("jetty.home", jettyHome);
        //
        JettyServerDemo jettyServer = new JettyServerDemo();
        jettyServer.initLog4j(jettyHome);
        jettyServer.startServer(jettyHome);

    }

    private void startServer(String jettyHome) throws Exception {
        XmlConfiguration configuration = new XmlConfiguration(
                new FileInputStream(new File(jettyHome + "/jetty/etc/jetty.xml")));
        Server server = (Server) configuration.configure();
        server.start();
        server.join();
    }

    private void initLog4j(String jettyHome) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File(jettyHome + "/conf/log4j.properties")));
        properties.setProperty("log4j.file.dir", "C:/log");
        PropertyConfigurator.configure(properties);
    }
}
