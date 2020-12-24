connect 'jdbc:derby:NewDBTest;create=true';
CREATE TABLE users(
    userid varchar(128) primary key, 
    passwd_digest varchar(128)
    );
CREATE TABLE customers(
    cust_id int not null primary key(START WITH 1, INCREMENT BY 1), 
    cust_name varchar(128)
    );
CREATE TABLE orders(
    order_id int not null primary key, 
    cust_id int not null(START WITH 1, INCREMENT BY 1),
    foreign key(cust_id) references customers(cust_id), 
    order_date date, 
    order_desc varchar(128)
    );

insert into users(userid, passwd_digest) values ('Testboy1', 'pass');
insert into customers(cust_id, cust_name) values (1, 'Johnny');
insert into orders(order_id, cust_id, order_date, order_desc) values (1, 1, CURRENT_DATE, 'A pool noodle');

insert into users(userid, passwd_digest) values ('Testboy2', 'pass');
insert into customers(cust_id, cust_name) values (2, 'Donny');
insert into orders(order_id, cust_id, order_date, order_desc) values (2, 2, CURRENT_DATE, 'A wheel of cheese');

insert into users(userid, passwd_digest) values ('Testboy3', 'pass');
insert into customers(cust_id, cust_name) values (2, 'Ringo');
insert into orders(order_id, cust_id, order_date, order_desc) values (3, 3, 12-12-1957, 'A drumset');