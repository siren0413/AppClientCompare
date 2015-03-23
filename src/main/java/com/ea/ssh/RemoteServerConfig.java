package com.ea.ssh;

import com.ea.Utils;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

public class RemoteServerConfig {

    private static final Logger log = Logger.getLogger(RemoteServerConfig.class);

    private static String host;
    private static Integer port;
    private static String user;
    private static String password;
    private static Session session;

    static {
        configure();
    }

    public static void configure() {
        try {
            Configuration cfg = new PropertiesConfiguration("app.config");
            host = cfg.getString("ssh.host", "localhost");
            user = cfg.getString("ssh.user", "root");
            password = cfg.getString("ssh.password", "");
            port = cfg.getInt("ssh.port", 22);
            log.info("Load remote server ssh configuration... OK --> " + Utils.getConfigurations(cfg, "ssh"));
        } catch (ConfigurationException e) {
            log.error("Load remote server ssh config... FAIL", e);
        }
    }

    private Session connect() {
        JSch jsch = new JSch();
        Session session = null;
        try {
            session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no"); // TODO: need to check host key for security reason.
            session.connect();
            if(session.isConnected()) {
                log.info("Connect and login to ssh server [" + host + ":" + port + "]... OK");
            }
        } catch (JSchException e) {
            log.error("Connect to ssh error",e);
        }
        return session;
    }

    public Session getSession(){
        if(session== null){
            session = connect();
        }
        return session;
    }
}
