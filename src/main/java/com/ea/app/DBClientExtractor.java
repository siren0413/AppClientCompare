package com.ea.app;

import com.ea.Constants;
import com.ea.ssh.Command;
import com.ea.ssh.RemoteServerExecutor;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DBClientExtractor {

    private static final Logger log = Logger.getLogger(DBClientExtractor.class);
    private static final String APP_CLIENT_CMD = "";
    private static final RemoteServerExecutor executor = new RemoteServerExecutor();

    public Map<String, String> extractXml() {
        Map<String, String> xmlMap = new HashMap<String, String>();
        List<String> list = listAllClients();
        for (String name : list) {
            if (xmlMap.containsKey(name)) {
                log.warn("Duplicate app clients[" + name + "] from mongo db... WARN");
                continue;
            }
            xmlMap.put(name, getXml(name));
        }
        log.info("Extract " + xmlMap.size() + " files from db... OK");
        return xmlMap;
    }

    public List<String> listAllClients() {
        List<String> list = new LinkedList<String>();
        Command cmd = new Command(Constants.EXEC_DIR, "ls", null);
        String output = executor.execCommand(cmd);
        BufferedReader br = new BufferedReader(new StringReader(output));
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
            br.close();
            log.info("List all clients size: [" + list.size() + "]... OK");
        } catch (IOException e) {
            log.error("IO error", e);
        }
        return list;
    }

    public String getXml(String fileName) {
        Command cmd = new Command(Constants.EXEC_DIR, "cat", fileName);
        String output = executor.execCommand(cmd);
        log.debug("Get XML for [" + fileName + "], size = " + output.getBytes().length + " bytes... OK");
        return output;
    }
}
