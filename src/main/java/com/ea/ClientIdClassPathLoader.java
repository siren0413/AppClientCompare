package com.ea;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.perforce.p4java.messages.PerforceMessages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ClientIdClassPathLoader {

    private static final int BUF_SIZE = 1024;

    public static Map<String, AppClient> loadXmlFromClassPath(){
        Map<String, AppClient> map = new HashMap<String, AppClient>();
        try {
            ClassPath cp = ClassPath.from(PerforceMessages.class.getClassLoader());
            ImmutableSet<ClassPath.ResourceInfo> set = cp.getResources();
            for (ClassPath.ResourceInfo info : set) {
                URL url = info.url();
                if ("jar".equals(url.getProtocol())) {
                    JarURLConnection connection = (JarURLConnection) url.openConnection();
                    String classPath = connection.getEntryName();
                    if(classPath.contains(Constants.CLIENT_ID_XML_CLASSPATH_DIRECTORY)){
                        InputStream in = ClientIdClassPathLoader.class.getClassLoader().getResourceAsStream(classPath);
                        StringBuilder out = new StringBuilder();
                        BufferedReader br = new BufferedReader(new InputStreamReader(in));
                        char[] buf = new char[BUF_SIZE];
                        int data = -1;
                        while ((data = br.read(buf)) != -1) {
                            out.append(buf,0,data);
                        }
                        //TODO: Xstream
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

}
