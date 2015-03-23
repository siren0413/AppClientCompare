package com.ea.xstream;

import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;

public class XmlSerializer {

    private static final Logger log = Logger.getLogger(XmlSerializer.class);

    public static String serialize(Object obj){
        XStream xstream = new XStream();
        return xstream.toXML(obj);
    }

    public static Object deSerialize(String xml){
        XStream xstream = new XStream();
        return xstream.fromXML(xml);
    }
}
