package com.database;

public class CassandraDBValidations extends CassandraTableBase {
   // private ValidateCassTable validateCassTable=null;
CassandraConnection cassandraConnection=new CassandraConnection();

public CassandraDBValidations(){
    cassandraConnection.connection("169.69.13.217");
    session=cassandraConnection.getSession();

}
}
