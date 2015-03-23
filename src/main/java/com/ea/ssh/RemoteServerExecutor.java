package com.ea.ssh;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RemoteServerExecutor extends RemoteServerConfig{

    private static final Logger log = Logger.getLogger(RemoteServerExecutor.class);
    private static final int BUF_SIZE = 1024;

    public String execCommand(Command cmd) {
        Session session = getSession();
        Channel channel = null;
        if (session.isConnected()) {
            try {
                channel = session.openChannel("exec");
                String cmdStr = CommandBuilder.build(cmd);
                ((ChannelExec) channel).setCommand(cmdStr);
                ((ChannelExec) channel).setErrStream(System.err);
                InputStream in = channel.getInputStream();
                channel.connect();

                if (channel.isConnected()) {
                    StringBuilder output = new StringBuilder();
                    char[] buf = new char[BUF_SIZE];
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    int data = -1;
                    while ((data =br.read(buf)) != -1) {
                        output.append(buf,0, data);
                    }
                    br.close();
                    log.debug("Exec command [" + cmdStr + "], output size: [" + output.toString().getBytes().length + "] bytes... OK");
                    return output.toString();
                }

            } catch (JSchException e) {
                log.error("Ssh session connect error", e);
                System.exit(0);
            } catch (IOException e) {
                log.error("IO error", e);
                System.exit(0);
            } finally {
                if (channel != null && channel.isConnected()) {
                    channel.disconnect();
                }
            }
        }
        return "";
    }
}
