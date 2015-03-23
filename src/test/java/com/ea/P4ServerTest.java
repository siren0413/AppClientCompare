package com.ea;

import com.ea.app.AppConfig;
import com.ea.p4.P4Server;
import com.perforce.p4java.exception.P4JavaException;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class P4ServerTest {

    P4Server server = new P4Server();

    {
        AppConfig.config();
    }

    public void testLogin() throws ConfigurationException, P4JavaException, URISyntaxException {
        server.login();
    }

    @Test
    public void testSyncFiles() throws ConfigurationException, P4JavaException, URISyntaxException, IOException {
        Map<String, String> map = server.syncFiles();
        //System.out.println(map);
    }


}
