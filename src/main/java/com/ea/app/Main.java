package com.ea.app;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Main {

    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
//        AppConfig.config();
//        DiffReportTool tool = new DiffReportTool();
//        tool.processDiff();
//        System.out.println(tool.report());
//        System.out.println("exit.");
//        System.exit(0);
        InputStream in = Main.class.getResourceAsStream("/com/perforce/p4java/messages/");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }
}
