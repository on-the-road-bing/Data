--增删改都需要手动提交一下
----#############增 insert
insert into person values(1,'王宇兵');
commit;
insert into person values(2,'王宇飞');
commit;

----#############改 update
update person set pname = '王宇花' where pid =1; 
commit;

----#############删 delete
--删除表中一条数据

delete from person where pid =2;
commit;

select * from person;



----############################修改表的属性
--1.给person表增加sex性别列，类型为number(1)
alter table person add sex number(1); 

--2.修改person表sex列为char(1)
alter table person modify sex char(1);

--3.修改person表sex列为gender
alter table person rename column sex to gender;

--4.删除person表的gender列
alter table person drop column gender;

--5.删除person表的所有数据

delete from person where 1=1;
commit;

--6.摧毁person表              （truncate table 表名）
/*
直接摧毁表结构后重构表，比delete要很快，但是没法按照条件删除
*/
truncate table  person;
