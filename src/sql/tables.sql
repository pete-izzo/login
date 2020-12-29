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
    foreign key(cust_id) references customers(cust_id), 
    order_date date(yyyy-mm-dd), 
    order_desc varchar(128)
    );

insert into users(userid, passwd_digest) values ('Testboy1', 'pass');
insert into customers(cust_id, cust_name) values (1, 'Johnny');
insert into orders(order_id, cust_id, order_date, order_desc) values (1, 1, CURRENT_DATE, 'A pool noodle');

insert into users(userid, passwd_digest) values ('Testboy2', 'pass');
insert into customers(cust_id, cust_name) values (2, 'Donny');
insert into orders(order_id, cust_id, order_date, order_desc) values (2, 2, CURRENT_DATE, 'A wheel of cheese');
insert into orders(order_id, cust_id, order_date, order_desc) values (3, 2, { d '2020-12-25' }, 'A Garden Hose');

insert into users(userid, passwd_digest) values ('Testboy3', 'pass');
insert into customers(cust_id, cust_name) values (3, 'Ringo');
insert into orders(order_id, cust_id, order_date, order_desc) values (4, 3, { d '1957-12-12' }, 'A drumset');

insert into users(userid, passwd_digest) values ('Ranch', 'dressing');
insert into customers(cust_id, cust_name) values (4, 'Casey Ryback');
insert into orders(order_id, cust_id, order_date, order_desc) values (5, 4, { d '1997-01-23' }, 'A Playstation');