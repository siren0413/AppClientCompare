package com.ea.app;

import com.ea.p4.P4Server;
import com.ea.xstream.XmlSerializer;
import com.ea.xstream.model.Person;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DiffReportTool {

    private int p4_fileSize;
    private int db_fileSize;
    private List<String> p4_residuals = new LinkedList<String>();
    private List<String> db_residuals = new LinkedList<String>();
    private List<String> diff_list = new LinkedList<String>();

    public String report(){
        StringBuilder sb = new StringBuilder();

        sb.append("-----------------------------------------\n");
        sb.append("Number of files: p4 = " + p4_fileSize +", db = "+db_fileSize +"\n");
        sb.append("Number of files diff from p4 and db: "+diff_list.size()+"\n");
        for(String file: diff_list){
            sb.append("\t--> " + file +"\n");
        }
        sb.append("Number of residual files on p4: "+ p4_residuals.size()+"\n");
        for(String file: p4_residuals){
            sb.append("\t--> " + file +"\n");
        }
        sb.append("Number of residual files on db: "+db_residuals.size()+"\n");
        for(String file: db_residuals){
            sb.append("\t--> " + file +"\n");
        }
        sb.append("-----------------------------------------\n");
        if(diff_list.size()==0 && p4_residuals.size()==0 && db_residuals.size()==0){
            sb.insert(0,"Consistency... OK\n");
        }else{
            sb.insert(0,"Inconsistency... ERROR\n");
        }
        sb.insert(0,"REPORT:\n");
        return sb.toString();
    }

    public void processDiff(){
        P4Server server = new P4Server();
        DBClientExtractor dbClientExtractor = new DBClientExtractor();
        Map<String,String> p4_map =  server.syncFiles();
        Map<String, String> db_map = dbClientExtractor.extractXml();
        p4_fileSize = p4_map.size();
        db_fileSize = db_map.size();

        for(Map.Entry<String,String> entry: p4_map.entrySet()){
            String key = entry.getKey();
            String p4_value = entry.getValue();
            if(!db_map.containsKey(key)){
                p4_residuals.add(key);
            }else {
                String db_value = db_map.get(key);
                if (p4_value != null && db_value!=null) {
                    Person p4_person = (Person) XmlSerializer.deSerialize(p4_value);
                    Person db_person = (Person) XmlSerializer.deSerialize(db_value);
                    if(!p4_person.equals(db_person)) {
                        diff_list.add(key);
                    }
                }
                db_map.remove(key);
            }
        }

        for(String key: db_map.keySet()){
            db_residuals.add(key);
        }

    }

}
