----############################Լ��
--1.����person��pidΪ������pname,gender     (����Լ��primary key)
--primary key ��ʽ
drop table person;
create table person(
   pid number primary key,
   pname varchar2(50),
   gender number
);
insert into person values(1,'wyb',1);
commit;

-- constraint ������ Primary key(�ֶΣ� ��ʽ
drop table person;
create table person(
   pid number ,
   pname varchar2(50),
   gender number,
   constraint PK_pid primary key(pid) 
   
);


--2.����person��pname�ǿգ�gender          (�ǿ�Լ�� not null)
drop table person;
create table person(
   pid number ,
   pname varchar2(50) not null,
   gender number 
);


--3.����person��pia,pname��Ψһ��gender   ��ΨһԼ��unique��
drop table person;
create table person(
   pid number ,
   pname varchar2(50) unique,
   gender number 
);


--4.����person��pia��pname��gender         ���Լ�� ��  check(���� in (ֵ))
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

----##########################���Լ��
/*
constraint fk_order_orderid forgein key(���) reference ��Ӧ�ı�(��Ӧ������)
*/

--1.����orders���ֶ�Ϊorder_id(����)��total_price
create table orders(
order_id number primary key,
total_price number(9,2)
)

--2.����order_detail���ֶ�Ϊdetail_id(����)��order_id(���)��item_name
create table order_detail(
  detail_id number primary key,
  order_id number,
  item_name varchar(50),
  constraint fk_order_order_id foreign key(order_id) references orders(order_id)
)

--3.Ϊorders���orders_detail���������  ���Ȳ���������ӱ�
insert into orders values(1,786.69);
commit;
insert into order_detail values(1,1,'�����');
commit;


--4.ɾ��orders���order_detail��������(��ɾ���ӱ���ɾ������)
delete from order_detail where 1=1;
commit;
delete from orders where order_id =1;
commit;
