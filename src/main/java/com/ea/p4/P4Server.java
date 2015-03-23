package com.ea.p4;

import com.ea.Constants;
import com.perforce.p4java.core.file.FileAction;
import com.perforce.p4java.core.file.FileSpecBuilder;
import com.perforce.p4java.core.file.FileSpecOpStatus;
import com.perforce.p4java.core.file.IFileSpec;
import com.perforce.p4java.exception.*;
import com.perforce.p4java.option.server.GetDepotFilesOptions;
import com.perforce.p4java.server.IOptionsServer;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P4Server extends P4ServerConfig {

    private static final Logger log = Logger.getLogger(P4Server.class);

    public IOptionsServer login() {
        IOptionsServer server = getOptionsServer(null);
        server.setUserName(userName);
        try {
            server.login(password);
            log.info("P4 server login... OK");
        } catch (Exception e) {
            log.error("P4 server login error", e);
        }
        return server;
    }

    public Map<String, String> syncFiles()  {
        IOptionsServer server = login();
        Map<String, String> map = new HashMap<String, String>();
        try {
            List<IFileSpec> fileList = server.getDepotFiles(FileSpecBuilder.makeFileSpecList(Constants.DEPOT_DIR + "*"), new GetDepotFilesOptions("-e"));
            log.info("Total latest revision files on " + Constants.DEPOT_DIR + ": " + fileList.size());
            char[] buf = new char[1024];
            for (IFileSpec fileSpec : fileList) {
                if (fileSpec != null && fileSpec.getOpStatus() == FileSpecOpStatus.VALID && fileSpec.getAction() != FileAction.DELETE) {
                    String fileName = getFileName(fileSpec.getPreferredPathString());
                    InputStream is = fileSpec.getContents(true);
                    BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                    StringBuilder out = new StringBuilder();
                    while (bf.read(buf) != -1) {
                        out.append(buf);
                    }
                    bf.close();
                    map.put(fileName, out.toString());
                }
            }
            log.info("Sync " + map.size() + " files from " + Constants.DEPOT_DIR + "... OK");
        } catch (Exception e){
            log.error("Sync files error from p4 server... FAIL", e);
            System.exit(0);
        }
        return map;
    }


    private String getFileName(String filePath) {
        int index = filePath.lastIndexOf(File.separator);
        return filePath.substring(index + 1, filePath.length());
    }
}
