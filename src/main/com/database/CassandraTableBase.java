package com.database;

import com.aventstack.extentreports.ExtentTest;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.google.common.collect.ArrayListMultimap;
import com.services.utils.ExtentReportsUtil;


import java.util.HashMap;

public abstract class CassandraTableBase {
    protected ResultSet resultSet;
    protected Session session;
    protected static ArrayListMultimap<Object, Object> multiMap=null;
    protected static HashMap<String,String> rsHashMap=null;
    protected ExtentTest reportTest(){
        return ExtentReportsUtil.reportTest();
    }

    public ResultSet getCassandraResultSet(String query){
        resultSet=null;

        try{
            resultSet=session.execute(query);

        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return  resultSet;
    }

    protected HashMap<String, String >rsHashMap(){
        if(rsHashMap==null){
            rsHashMap=new HashMap<>();
        }
        return rsHashMap;
    }

    public ArrayListMultimap<Object, Object> resultSetToListMultimap(String cQuery){
        resultSet=getCassandraResultSet(cQuery);

        try{
            multiMap= ArrayListMultimap.create();
            while(resultSet.iterator().hasNext()){
                Row row=resultSet.iterator().next();
                for(int i=0;i<row.getColumnDefinitions().size();i++){
                    System.out.println("Type : "+row.getColumnDefinitions().getType(i));
                    switch (row.getColumnDefinitions().getType(i).toString()){
                        case "varchar":
                            row.getString(i);
                            multiMap.put(row.getColumnDefinitions().getName(i),row.getString(i));
                            break;
                        case "boolean":
                            row.getBool(i);
                            multiMap.put(row.getColumnDefinitions().getName(i),row.getBool(i));
                            break;
                        case "timestamp":
                            row.getBool(i);
                            multiMap.put(row.getColumnDefinitions().getName(i),row.getTimestamp(i).toString());
                            break;
                        default:
                            return null;
                    }
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return multiMap;
    }
}
