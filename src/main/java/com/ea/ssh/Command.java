package com.ea.ssh;

import java.util.List;
import java.util.Map;

public class Command {
    private String execDir;
    private String cmd;
    private String target;
    private List<String> args;
    private Map<String, String> execEnv;

    public Command() {
    }

    public Command(String execDir, String cmd, String target) {
        this.execDir = execDir;
        this.cmd = cmd;
        this.target = target;
    }

    public Command(String execDir, String cmd, String target, List<String> args, Map<String, String> execEnv) {
        this.execDir = execDir;
        this.cmd = cmd;
        this.target = target;
        this.args = args;
        this.execEnv = execEnv;
    }


    public String getExecDir() {
        return execDir;
    }

    public void setExecDir(String execDir) {
        this.execDir = execDir;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    public Map<String, String> getExecEnv() {
        return execEnv;
    }

    public void setExecEnv(Map<String, String> execEnv) {
        this.execEnv = execEnv;
    }

    @Override
    public String toString() {
        return "Command{" +
                "execDir='" + execDir + '\'' +
                ", cmd='" + cmd + '\'' +
                ", target='" + target + '\'' +
                ", args=" + args +
                ", execEnv=" + execEnv +
                '}';
    }
}
