CREATE USER V_KUMAR IDENTIFIED BY test_password;

GRANT ALL PRIVILEGES TO V_KUMAR;

CREATE TABLE ROLE
( id number(3) NOT NULL primary key,
  role_name varchar2(50) NOT NULL
);

CREATE TABLE USERS( 
  user_name varchar2(50) NOT NULL primary key,
  user_password varchar2(500) NOT NULL,
  user_first_name varchar2(50) NOT NULL,
  user_last_name varchar2(50) NOT NULL,
  last_update_time timestamp not null,
  user_role number(3) not null,
  version number(3) not null,
  CONSTRAINT fk_role
    FOREIGN KEY (user_role)
    REFERENCES ROLE(id)
);

CREATE TABLE FILES( 
  ID number(3) NOT NULL primary key,
  file_name varchar2(50) NOT NULL,
  file_type varchar2(50) NOT NULL,
  data blob NOT NULL
);

CREATE SEQUENCE USER_SEQ
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 
 CREATE SEQUENCE ROLE_SEQ
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 
  CREATE SEQUENCE FILE_SEQ
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 
  insert into USERS (user_name ,  user_password ,  user_first_name ,  user_last_name ,  last_update_time ,   user_role)  
 values('vkumar', 'DGn0gxjDKlZ9m7SiJIRiiiRfn+jstcTmDV71Z2X+gPI=$GFcUWJdnua1C7j4dbSmWE9E/rg+xd/rL4bXQSQMzy+A=', 'vivek', 'kumar', systimestamp, 1);