package com.ea;

import com.ea.ssh.Command;
import com.ea.ssh.CommandBuilder;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CommandTest {

    @Test
    public void testCmdBuild(){
        Command cmd = new Command("/opt/nexus.tools/bin","ls",null);
        List<String> args = new LinkedList<String>();
        args.add("-list");
        args.add("-la");
        cmd.setArgs(args);
        Map<String,String> map = new HashMap<String, String>();
        map.put("VAR","123");
        map.put("VAR1","213");
        cmd.setExecEnv(map);
        String result = CommandBuilder.build(cmd);
        System.out.println(result);
    }
}
