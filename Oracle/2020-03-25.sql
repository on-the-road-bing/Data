----############################约束
--1.创建person表，pid为主键，pname,gender     (主键约束primary key)
--primary key 方式
drop table person;
create table person(
   pid number primary key,
   pname varchar2(50),
   gender number
);
insert into person values(1,'wyb',1);
commit;

-- constraint 主键名 Primary key(字段） 方式
drop table person;
create table person(
   pid number ,
   pname varchar2(50),
   gender number,
   constraint PK_pid primary key(pid) 
   
);


--2.创建person表，pname非空，gender          (非空约束 not null)
drop table person;
create table person(
   pid number ,
   pname varchar2(50) not null,
   gender number 
);


--3.创建person表，pia,pname是唯一，gender   （唯一约束unique）
drop table person;
create table person(
   pid number ,
   pname varchar2(50) unique,
   gender number 
);


--4.创建person表，pia，pname，gender         检查约束 ：  check(列名 in (值))
drop table person;
create table person(
   pid number ,
   pname varchar2(50),
   gender number check(gender in(0,1))
);

insert into person values(1,'wyf',1);
commit;

insert into person values(2,'wyb',0);
commit;

----##########################外键约束
/*
constraint fk_order_orderid forgein key(外键) reference 对应的表(对应的主键)
*/

--1.创建orders表，字段为order_id(主键)，total_price
create table orders(
order_id number primary key,
total_price number(9,2)
)

--2.创建order_detail表，字段为detail_id(主键)，order_id(外键)，item_name
create table order_detail(
  detail_id number primary key,
  order_id number,
  item_name varchar(50),
  constraint fk_order_order_id foreign key(order_id) references orders(order_id)
)

--3.为orders表和orders_detail表插入数据  （先插入主表，后从表）
insert into orders values(1,786.69);
commit;
insert into order_detail values(1,1,'照相机');
commit;


--4.删除orders表和order_detail表中数据(先删除从表，后删除主表)
delete from order_detail where 1=1;
commit;
delete from orders where order_id =1;
commit;
