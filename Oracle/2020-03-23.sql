----###############################����ѯ(�����ӡ�������)
---1.��������
--��ѯԱ����ţ�Ա��������Ա�����ű�ţ�Ա���������ƣ�Ա�����ŵ�ַ��������ʾԱ�����ʵȼ������쵼��ţ��쵼�������쵼���ű�ţ��쵼�������ƣ�������ʾ�쵼���ʵȼ�
select e1.empno,e1.ename,e1.deptno,d1.dname,d1.loc,decode(s1.grade,1,'һ��',2,'����',3,'����',4,'�ļ�',5,'�弶')"sallevel",   --ֻ�б�����˫����
e1.mgr,e2.ename,e2.deptno,d2.dname,decode(s2.grade,1,'һ��',2,'����',3,'����',4,'�ļ�',5,'�弶') "sallevel"
from emp e1,dept d1,emp e2,dept d2��salgrade s1,salgrade s2
where e1.deptno = d1.deptno 
and e1.sal between s1.losal and s1.hisal 
and e1.mgr = e2.empno 
and e2.deptno =d2.deptno 
and e2.sal between s2.losal and s2.hisal;


----#####################################������
----1.��ѯԱ����ţ��������쵼��ţ��쵼����������û�쵼��
--left join...on ��ʽ
select e1.empno,e1.ename,e2.empno,e2.ename from emp e1,emp e2 where e1.mgr = e2.empno;  --��������û�쵼�ģ�����
select e1.empno,e1.ename,e2.empno,e2.ename from emp e1 left join emp e2 on e1.mgr = e2.empno;  --������û�쵼�ģ�

--oracle�ģ�+����ʽ    :��ûд�Ӻŵ�һ��Ϊ׼
select e1.empno,e1.ename,e2.empno,e2.ename from emp e1,emp e2 where e1.mgr = e2.empno(+);

----2.��ѯ�����в�����Ϣ������ûԱ���Ĳ��ţ��������µ�Ա����Ϣ
select * from emp,dept where emp.deptno(+) = dept.deptno;


----####################################################�Ӳ�ѯ
--1.��ѯ��Ա��7654���ʸߣ�ͬʱ���º�7788�Ĺ���һ����Ա��empno =7788
select * from emp where sal > (select sal from emp where empno =7654)
and job = (select job from emp where empno =7788);

--2.��ѯÿ��������͹��ʼ���͹��ʵĲ������ƺ�Ա����Ϣ
select * from (select min(sal) minsal,deptno from emp group by deptno) e1,emp,dept
where emp.deptno =dept.deptno and dept.deptno = e1.deptno  and emp.sal=e1.minsal;

select minsal,emp.deptno,dname,ename from (select min(sal) minsal,deptno from emp group by deptno) e1,emp,dept
where emp.deptno =dept.deptno and dept.deptno = e1.deptno  and emp.sal=e1.minsal;


----#########################������ϰ
--1.�ҵ�Ա�����й�����ߵ�ǰ����
 select rownum, e.* from (select emp.* from emp order by sal desc) e where rownum <= 3;

--2.�ҵ�Ա������нˮ���ڱ�����ƽ�����ʵ�����Ա��
select emp.empno,emp.ename,e.deptno,e.averagesal,emp.sal from (select avg(sal) averagesal,deptno from emp group by deptno) e,emp
where emp.deptno = e.deptno and emp.sal > e.averagesal
 
--3.ͳ��ÿ����ְ��Ա������
select count(*),to_char(hiredate,'yyyy') from emp group by to_char(hiredate,'yyyy');
