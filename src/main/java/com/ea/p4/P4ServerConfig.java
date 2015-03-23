package com.ea.p4;

import com.ea.Constants;
import com.ea.Utils;
import com.perforce.p4java.option.UsageOptions;
import com.perforce.p4java.server.IOptionsServer;
import com.perforce.p4java.server.ServerFactory;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;


public abstract class P4ServerConfig {

    private static final Logger log = Logger.getLogger(P4ServerConfig.class);

    protected static String serverUri;
    protected static String userName;
    protected static String password;

    static{
        configure();
    }

    protected static void configure()  {
        try {
            Configuration configuration = new PropertiesConfiguration(Constants.CONFIG_FILE);
            serverUri = configuration.getString("p4.serverUri","");
            userName = configuration.getString("p4.userName","");
            password = configuration.getString("p4.password","");
            log.info("Load p4 server configuration... OK --> " + Utils.getConfigurations(configuration, "p4"));
        } catch (ConfigurationException e) {
            log.error("Load p4 server configuration... FAIL", e);
            System.exit(0);
        }
    }

    protected static IOptionsServer getOptionsServer(UsageOptions opts) {
        IOptionsServer server = null;
        try {
            server = ServerFactory.getOptionsServer(serverUri, null, opts);
            if (server != null) {
                server.connect();
            }
            log.info("Connect to p4 server... OK");
        } catch (Exception e) {
            log.error("P4 server connect error", e);
            System.exit(0);
        }
        return server;
    }

}
