package com.ea.ssh;

import org.apache.log4j.Logger;

import java.util.Map;

public class CommandBuilder {

    private static final Logger log = Logger.getLogger(CommandBuilder.class);

    private static final String SPACE = " ";
    private static final String CMD_SEPARATOR = "&&";

    public static String build(Command cmd){
        StringBuilder sb = new StringBuilder();
        if(cmd.getExecDir()!=null) {
            sb.append(build(new Command(null, "cd", cmd.getExecDir()))); // build cmd to change dir(cd) to EXEC_DIR
            sb.append(CMD_SEPARATOR);
        }
        sb.append(SPACE);
        if(cmd.getExecEnv()!=null && !cmd.getExecEnv().isEmpty()){
            for(Map.Entry<String,String> entry: cmd.getExecEnv().entrySet()){
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append(SPACE).append(CMD_SEPARATOR).append(SPACE);
            }
        }
        sb.append(SPACE);
        if(cmd.getCmd()!=null){
            sb.append(cmd.getCmd());
        }
        sb.append(SPACE);
        if(cmd.getTarget()!=null){
            sb.append(cmd.getTarget());
        }
        sb.append(SPACE);
        if(cmd.getArgs()!=null && !cmd.getArgs().isEmpty()){
            for(String arg: cmd.getArgs()){
                sb.append(arg).append(SPACE);
            }
        }
        sb.append(SPACE);
        log.debug("build command ["+sb.toString()+"] from " + cmd);
        return sb.toString();
    }

}
