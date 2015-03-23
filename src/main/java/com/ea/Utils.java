package com.ea;

import org.apache.commons.configuration.Configuration;
import java.util.Iterator;

public class Utils {

    public static String getConfigurations(Configuration cfg, String prefix) {
        StringBuilder sb = new StringBuilder().append("{");
        Iterator<String> iter = cfg.getKeys();
        while(iter.hasNext()){
            String key = iter.next();
            if(prefix!=null && !key.startsWith(prefix)){
                continue;
            }
            String value = cfg.getString(key);
            sb.append(" {"+key+"="+value+"} ");
        }
        sb.append("}");
        return sb.toString();
    }
}
