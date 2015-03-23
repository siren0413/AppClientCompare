package com.ea;

import com.ea.ssh.Command;
import com.ea.ssh.RemoteServerExecutor;
import org.junit.Test;

public class RemoteServerTest {

    @Test
    public void testExecCmd(){
        Command cmd = new Command("/opt/nexus.tools/bin","cat","test.xml");
        RemoteServerExecutor executor = new RemoteServerExecutor();
        String output = executor.execCommand(cmd);
        System.out.println(output);
    }

}
