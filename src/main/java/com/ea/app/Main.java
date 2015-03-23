package com.ea.app;

import org.apache.log4j.Logger;


public class Main {

    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args){
        AppConfig.config();
        DiffReportTool tool = new DiffReportTool();
        tool.processDiff();
        System.out.println(tool.report());
        System.out.println("exit.");
        System.exit(0);
    }
}
