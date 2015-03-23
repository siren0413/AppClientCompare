package com.ea;

import com.ea.ssh.Command;
import com.ea.app.DBClientExtractor;
import com.ea.ssh.RemoteServerExecutor;
import com.ea.xstream.XmlSerializer;
import com.ea.xstream.model.Person;
import com.ea.xstream.model.PhoneNumber;
import org.junit.Test;

public class XmlSerializerTest {

    @Test
    public void testSerialize(){
        Person joe = new Person("Joe", "Walnes");
        joe.setPhone(new PhoneNumber(123, "1234-456"));
        joe.setFax(new PhoneNumber(123, "9999-999"));
        String xml = XmlSerializer.serialize(joe);
        System.out.println(xml);
    }

    @Test
    public void testDeSerialize(){
        Command cmd = new Command("/opt/nexus.tools/bin","cat","test.xml");
        DBClientExtractor server = new DBClientExtractor();
        RemoteServerExecutor executor = new RemoteServerExecutor();
        String output = executor.execCommand(cmd);
        Person p = (Person)XmlSerializer.deSerialize(output);
        System.out.println(p);
    }
}
