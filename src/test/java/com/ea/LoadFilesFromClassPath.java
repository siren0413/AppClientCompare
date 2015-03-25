package com.ea;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.perforce.p4java.messages.PerforceMessages;
import org.junit.Test;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

public class LoadFilesFromClassPath {

    //@Test
    public void test() throws IOException {
        InputStream in = LoadFilesFromClassPath.class.getResourceAsStream("/com/perforce/p4java/messages/ClientTrustMessages.properties");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    //@Test
    public void test2() throws IOException {
//        Collection<File> files = FileUtils.listFiles(new File("/com/perforce/p4java/messages"), null, false);
//        System.out.println(files);java.util.Enumeration<java.net.URL>
        InputStream in = LoadFilesFromClassPath.class.getResourceAsStream("/com/perforce/p4java/admin/");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    @Test
    public void test3() throws IOException, URISyntaxException {
        ClassPath cp = ClassPath.from(PerforceMessages.class.getClassLoader());
        ImmutableSet<ClassPath.ResourceInfo> set = cp.getResources();
        for (ClassPath.ResourceInfo info : set) {
            URL url = info.url();
            if (url.getProtocol().equals("jar")) {
                JarURLConnection connection = (JarURLConnection) url.openConnection();
                String classPath = connection.getEntryName();
                if(classPath.contains("com/perforce/p4java/messages/")){
                    System.out.println(classPath);
                    InputStream in = LoadFilesFromClassPath.class.getClassLoader().getResourceAsStream(classPath);
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            }
        }
    }

    public void test4() throws IOException {

    }


}
