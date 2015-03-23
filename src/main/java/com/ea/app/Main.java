package com.ea.app;

import com.ea.p4.P4Server;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args){
        AppConfig.config();
        DiffReportTool tool = new DiffReportTool();
        tool.processDiff();
        System.out.println(tool.report());
    }
}
