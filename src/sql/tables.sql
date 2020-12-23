CREATE DATABASE NewTestDB;
CREATE TABLE users(userid varchar(128) primary key, passwd_digest varchar(128));
CREATE TABLE customers(cust_id int not null auto_increment primary key, cust_name varchar(128));
CREATE TABLE orders(order_id int auto_increment primary key, cust_id int foreign key references customers(cust_id), order_date date, order_desc varchar(128));

insert into users(userid, passwd_digest) values ('Testboy1', 'pass');
insert into customers(cust_name) values ( 'Johnny');
insert into orders(order_date, order_desc) values (curdate(), 'A pool noodle');