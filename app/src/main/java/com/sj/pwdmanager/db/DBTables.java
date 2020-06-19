package com.sj.pwdmanager.db;

public interface DBTables {
    String DB_NAME = "pwd.sqlite";
    int DB_VERSION = 1;
    String TABLE_ACCOUNT = "pwd";
    String ACCOUNT_SQL = "create table "+TABLE_ACCOUNT+"(" +
            "account varchar(100)," +
            "pwd varchar(200)," +
            "belong varchar(200)," +
            "type varchar(10),"+
            "primary key (account,belong,type)" +
            ")";
    String DROP_ACCOUNT = "DROP TABLE IF EXISTS"+TABLE_ACCOUNT;
}
