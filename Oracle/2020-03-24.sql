--��ɾ�Ķ���Ҫ�ֶ��ύһ��
----#############�� insert
insert into person values(1,'�����');
commit;
insert into person values(2,'�����');
commit;

----#############�� update
update person set pname = '���' where pid =1; 
commit;

----#############ɾ delete
--ɾ������һ������

delete from person where pid =2;
commit;

select * from person;



----############################�޸ı������
--1.��person������sex�Ա��У�����Ϊnumber(1)
alter table person add sex number(1); 

--2.�޸�person��sex��Ϊchar(1)
alter table person modify sex char(1);

--3.�޸�person��sex��Ϊgender
alter table person rename column sex to gender;

--4.ɾ��person���gender��
alter table person drop column gender;

--5.ɾ��person�����������

delete from person where 1=1;
commit;

--6.�ݻ�person��              ��truncate table ������
/*
ֱ�Ӵݻٱ�ṹ���ع�����deleteҪ�ܿ죬����û����������ɾ��
*/
truncate table  person;
