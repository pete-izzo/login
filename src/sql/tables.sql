connect 'jdbc:derby:NewDBTest;create=true';
CREATE TABLE users(
    userid varchar(128) primary key, 
    passwd_digest varchar(128)
    );
CREATE TABLE customers(
    cust_id int GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) primary key, 
    cust_name varchar(128)
    );
CREATE TABLE orders(
    order_id int GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) primary key, 
    cust_id int,
    foreign key(cust_id) references customers(cust_id), 
    order_date date, 
    order_desc varchar(128)
    );

insert into users(userid, passwd_digest) values ('Testboy1', 'pass');
insert into customers(cust_name) values ('Johnny');
insert into orders(cust_id, order_date, order_desc) values (1, CURRENT_DATE, 'A pool noodle');

insert into users(userid, passwd_digest) values ('Testboy2', 'pass');
insert into customers(cust_name) values ('Donny');
insert into orders(cust_id, order_date, order_desc) values (2, CURRENT_DATE, 'A wheel of cheese');
insert into orders(cust_id, order_date, order_desc) values (2, { d '2020-12-25' }, 'A Garden Hose');

insert into users(userid, passwd_digest) values ('Testboy3', 'pass');
insert into customers(cust_name) values ('Ringo');
insert into orders(cust_id, order_date, order_desc) values (3, { d '1957-12-12' }, 'A drumset');

insert into users(userid, passwd_digest) values ('Ranch', 'dressing');
insert into customers(cust_name) values ('Casey Ryback');
insert into orders(cust_id, order_date, order_desc) values (4, { d '1997-01-23' }, 'A Playstation');

--new donny
insert into customers(cust_name) values ('Donny');
insert into orders(cust_id, order_date, order_desc) values (5, { d '2001-03-25' }, 'A Garden Gnome');
insert into orders(cust_id, order_date, order_desc) values (5, { d '2011-04-03' }, 'A boat');
