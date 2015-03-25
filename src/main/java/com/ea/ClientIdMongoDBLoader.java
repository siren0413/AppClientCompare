package com.ea;

import com.google.common.collect.ImmutableList;
import com.google.common.io.CharSource;
import com.google.common.io.CharStreams;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ClientIdMongoDBLoader {
    public static Map<String, AppClient> loadXmlFromDB(){
        Map<String, AppClient> map = new HashMap<String, AppClient>();
        List<String> list = listClientIds();
        for(String clientId: list){
            CommandLine cmd = new CommandLine("clientconfig");
            cmd.addArgument("-g");
            cmd.addArgument(clientId);
            String output = exec(cmd,Constants.CLIENT_ID_EXEC_DIR);
            //TODO: Xstream
        }
        return map;
    }

    private static List<String> listClientIds(){
        String output = exec("sh clientconfig -list", Constants.CLIENT_ID_EXEC_DIR);
        CharSource sc = CharSource.wrap(output);
        try {
            ImmutableList<String> list = sc.readLines();
            int start = list.indexOf("list all client id:") + 1;
            list.subList(start, list.size());
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new LinkedList<String>();
    }

    public static String exec(String command, String workingDir)  {
        CommandLine commandline = CommandLine.parse(command);
        return exec(commandline, workingDir);
    }

    public static String exec(CommandLine commandline, String workingDir){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DefaultExecutor exec = new DefaultExecutor();
        exec.setWatchdog(new ExecuteWatchdog(5000));
        exec.setWorkingDirectory(new File(workingDir));
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
        exec.setStreamHandler(streamHandler);
        try {
            exec.execute(commandline);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (outputStream.toString());
    }
}
