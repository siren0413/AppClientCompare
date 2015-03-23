package com.ea.app;

import com.ea.Constants;
import com.ea.Utils;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

public class AppConfig {

    private static final Logger log = Logger.getLogger(AppConfig.class);

    public static void config()  {
        try {
            Configuration cfg = new PropertiesConfiguration(Constants.CONFIG_FILE);
            Constants.DEPOT_DIR = cfg.getString("app.depot_dir","//depot/");
            Constants.EXEC_DIR = cfg.getString("app.exec_dir","/");
            log.info("Load app context configuration... OK --> " + Utils.getConfigurations(cfg, "app"));
        } catch (ConfigurationException e) {
            log.error("Load app context configuration... FAIL", e);
        }
    }
}
