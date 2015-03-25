package com.ea;

import org.apache.commons.exec.*;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.*;

public class ProcessBuilderTest {

    private static final Logger log = Logger.getLogger(ProcessBuilderTest.class);

    //@Test
    public void processTest() throws IOException {
        DefaultExecutor executor = new DefaultExecutor();
        executor.setExitValues(null);
        ByteArrayOutputStream outStream=new ByteArrayOutputStream(1024);
        ByteArrayOutputStream errStream=new ByteArrayOutputStream(1024);
        executor.setStreamHandler(new PumpStreamHandler(outStream,errStream));
        int timeout = 5000;
        ExecuteWatchdog watchdog = new ExecuteWatchdog(timeout);
        executor.setWatchdog(watchdog);
        CommandLine cmd = new CommandLine(new File("/Users/yijunmao/Desktop/list.sh"));
        System.out.println("running: " + cmd);
        int exitCode = executor.execute(cmd);
        System.out.println("exit code: " + exitCode);
    }

    @Test
    public void test2() throws Exception {
        //execute("/Users/yijunmao/Desktop/","sh list.sh",5000);
        //slurpOutput(new CommandLine("ls -la"),true);
        CommandLine cmd = new CommandLine("list.sh");
        System.out.println(execToString("sh list.sh","/Users/yijunmao/Desktop"));
    }

    public void execute(String workingFolder,String commandLineStr,long timeOut) {
        log.info("Working folder :[" + workingFolder + "]");
        log.info("Command line :[ " + commandLineStr + "]");
        CommandLine commandLine=CommandLine.parse(commandLineStr);
        ExecuteWatchdog watchdog=new ExecuteWatchdog(timeOut);
        DefaultExecutor executor=new DefaultExecutor();
        executor.setWorkingDirectory(new File(workingFolder));
        executor.setWatchdog(watchdog);
        try {
            log.info("Executing command line [" + commandLine + "]");
            long startTime=System.currentTimeMillis();
            int exitValue=executor.execute(commandLine);
            long endTime=System.currentTimeMillis();
            long diffTime=endTime - startTime;
            log.debug(commandLine + " ran for " + diffTime+ " mills");
        }
        catch (  ExecuteException e) {

        }
        catch (  IOException e) {
            e.printStackTrace();
        }
    }


    public String execToString(String command, String workingDir) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CommandLine commandline = CommandLine.parse(command);
        DefaultExecutor exec = new DefaultExecutor();
        exec.setWatchdog(new ExecuteWatchdog(5000));
        exec.setWorkingDirectory(new File(workingDir));
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
        exec.setStreamHandler(streamHandler);
        exec.execute(commandline);
        return (outputStream.toString());
    }



}
