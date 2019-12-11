package com.database;

import com.aventstack.extentreports.ExtentTest;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.services.utils.EnviornmentUtility;
import com.services.utils.ExtentReportsUtil;


public class CassandraConnection {
    private Cluster cluster;
    private Session session;
    protected ResultSet rs=null;
    String userID=null;
    String password=null;
    protected ExtentTest reportTest(){
        return ExtentReportsUtil.reportTest();
    }

    public CassandraConnection connection(String... contactPoints){
        String contactPoint=null;
        for(String contact:contactPoints){
            contactPoint=contact+",";

        }
        Cluster.Builder b=Cluster.builder().addContactPoints(contactPoint).withCredentials(userID,password).withSSL();
        cluster=b.build();
        session=cluster.connect();
        System.out.println("You are connected...");
        return this;

    }

    public CassandraConnection connection(String  contactPoint){
        Cluster.Builder b=Cluster.builder().addContactPoints(contactPoint).withCredentials(userID,password).withSSL();
        cluster=b.build();
        session=cluster.connect();
        System.out.println("You are connected...");
        return this;

    }

    public Session getSession(){
        return this.session;
    }

    public CassandraConnection close(){
        session.close();
        cluster.close();
        return this;
    }
}
