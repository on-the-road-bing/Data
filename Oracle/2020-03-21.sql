---###################################################һ�����ݿ������ѯselect��distinct��nvl��||��concat
select * from emp;

--��ѯemp�е���Ϣ�����������ֶ�������
select empno "Ա�����"��ename "����",job "ְλ"��mgr "�쵼���"��hiredate "��ְ����",sal "����"��comm "����",deptno "���ű��"from emp;   --ֻ�б�����˫����

--��ѯemp����job��Ϣ����ȥ���ظ���Ϣ
select distinct(job) from emp;

--��ѯemp����Ա����ȫ��Ĺ����ܺ�(sal�ܺͣ�
select ename,sal*12 from emp;

--��ѯemp��Ա����ȫ�������ܺͣ�sal+comm)
select ename,sal*12+comm from emp;--���������⣬�е�Ա��commΪ��ֵ
select ename,sal*12+nvl(comm,0) from emp;

--��ѯemp����Ա����ţ������������ʽΪ����ţ�xxx��������xxx
-----concatƴ�ӷ�ʽ
select concat(concat('Ա����ţ�',empno),concat(',������',ename��) from emp;

--oracle��\\��ʽ
select '���:'||empno||'������:'||ename from emp;


-----################################################����������ѯ��where >= ,<= ,!= ,<>
----��ѯ���ʴ���1500��Ա��
select * from emp where sal>=1500;

----��ѯ���ʴ���1500�����н����Ա��
select * from emp where sal>=1500 and comm is not null;

----��ѯ���ʴ���1500�����н����Ա��
select * from emp where sal>=1500 or comm is  not null;

----��ѯ���ʴ���1500����û�����Ա��
select * from emp where sal>=1500 and comm is  null;

----��ѯ����Ϊsmith��Ա��
select * from emp where ename ='smith'; 




-----################################################������Χ��ѯ:between...and,  in,     is null,     is not null
--1.>=,<=��ʽ
select *from emp where sal>=1500 and sal<=3000;

--2.between and ��ʽ
select * from emp where sal between 1500 and 3000; 

--��ѯ 1981-1-1��1981-12-21����ְ��Ա�� 
select * from emp where hiredate between to_date('1981-1-1','yyyy-mm-dd') and to_date('1981-12-21','yyyy-mm-dd');

--��ѯԱ�������7369,7654,7566��Ա��
-----1.or��ʽ
select * from emp where empno=7369 or empno =7654 or empno = 7566;
-----2.in��ʽ
select * from emp where empno in(7369,7654,7566);

--��ѯ������'smith,allen,ward'��ѧ��           �ַ���ע���Сд

select * from emp where ename in('SMITH','ALLEN','WARD');




------##############################################�ġ�ģ����ѯ��like   '%'
--��ѯ����Ա�������еڶ����ַ���'M'��Ա��
select * from emp where ename like '_M%';

--��ѯԱ����������'M"��Ա��
select * from emp where ename like '%M%';

--��ѯԱ����Ų���7369��Ա����Ϣ
----1.<>��ʽ
select * from emp where empno <> 7369;
----2.!=��ʽ
select * from emp where empno != 7369;



----################################################�塢����order by
--��ѯԱ�����ʽ��н�������Ĭ������   
select ename,sal from emp order by sal desc;

--��ѯԱ���������������򣨹���nulls first/nulls last),nulls firstĬ����ǰͷ
select ename,comm from emp order by comm desc nulls last;

--��ѯԱ���������������������н��𲿷�����������
select ename,sal,comm from emp order by sal desc,comm asc;



-----##############################################�������ں�����sysdate��add_months()��months_between()
--��ѯϵͳʱ��                �ؼ���sysdate
select sysdate from dual;                       --dualΪα��

--��ѯԱ�����빫˾����
select ename,hiredate,(sysdate-hiredate)/7 from emp;

--��ѯԱ�����빫˾����        �ؼ���months_between
select ename,hiredate,months_between(sysdate,hiredate) from emp;

--���Ա����ְ�����º������  �ؼ���add_months
select ename,hiredate,add_months(hiredate,3) from emp;




----###############################################�ߡ����к���һ��ת������  :to_char()   ,  to_date()   , to_number()
--------1.������ת�ַ������ַ���ת���ڣ�

--��ϵͳʱ����ʾΪyyyy-mm-dd hh:mi:ss      �ؼ���to_char
select to_char(sysdate,'yyyy-mm-dd hh:mi:ss') from dual;

--��ʾ��������
select to_char(sysdate,'yyyy')||'��'||to_char(sysdate,'mm')||'��'||to_char(sysdate,'dd')||'��' from dual;

--���ַ���'1981-1-2'ת������������          �ؼ���to_date
select to_date('1981-1-2','yyyy-mm-dd') from dual;

---------2.(�ַ���ת��ֵ����ֵת�ַ���)
select to_number('99') from dual;   --�ַ���ת��ֵ
select to_char(99) from dual;       --��ֵת�ַ���


---#################################################���к�������ͨ�ú���
--1.��ֵ�Ĵ�����     nvl
select ename,nvl(comm,0) from emp;

--2.nvl2(�ж�ֵ,�շ���ֵ���ǿշ���ֵ)
select nvl2(null,1,2) from dual;
select ename,nvl2(comm,1,2) from emp;


----�������ʽ
--1. ��ѯԱ����job���ݲ�ת��������ʾ
-----decode��ʽ
select ename,decode(job,'CLERK','��Ա'��'SALESMAN','����'��'MANAGER','����'��'����') from emp;

--2. case..when..then..end��ʽ
select ename,case job when 'CLERK' then '��Ա'
when 'SALESMAN' then '����'
  when 'MANAGER'then '����'
    else 
      '����'
      end from emp;



----#################################################�ˡ����к���һ��count() ,  sum(),  avg(),  max(),  min()
--1.��ѯ����Ա����¼��              ������м�¼��������
select count(*) from emp;

--2.��ѯӶ����������β�ѯĳ���ֶεĶ���������
select sum(comm) from emp;

--3.��ѯ��͹���          �ؼ���min
select min(sal) from emp;
--4.��ѯ��߹���          �ؼ���max
select max(sal) from emp;

--5.��ѯƽ������          �ؼ���avg
select avg(sal) from emp;

--6.��ѯ20�Ų��ŵ�Ա�������ܺ�
select sum(sal) from emp where deptno =20;


----###################################################���к������������ѯ��group  by��
--1.��ѯ���ű�ż�����  ----- �����ѯ�ؼ���group by
select deptno,count(*) from emp group by deptno;

--2.��ѯÿ�����ű�ż�ƽ������
select deptno,avg(sal) from emp group by deptno;

--3.��ѯ�������ƣ����ű�ţ�ƽ������
select dname,emp.deptno,avg(sal) from emp,dept where emp.deptno=dept.deptno group by emp.deptno,dname;


-----###################################################���к����������ˣ�having��having
--4.��ѯ������������5�˵Ĳ���
select deptno,count(*) from emp group by deptno having count(*)>5; 

--5.��ѯ���ű�ţ��������ƣ�ƽ��������ƽ�����ʴ���2000
select emp.deptno,dname,avg(sal) from dept,emp where emp.deptno =dept.deptno group by  emp.deptno,dname having avg(sal)>=2000;




