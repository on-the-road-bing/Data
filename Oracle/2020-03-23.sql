----###############################多表查询(内连接、外连接)
---1.六表联查
--查询员工编号，员工姓名，员工部门编号，员工部门名称，员工部门地址，中文显示员工工资等级，及领导编号，领导姓名，领导部门编号，领导部门名称，中文显示领导工资等级
select e1.empno,e1.ename,e1.deptno,d1.dname,d1.loc,decode(s1.grade,1,'一级',2,'二级',3,'三级',4,'四级',5,'五级')"sallevel",   --只有别名用双引号
e1.mgr,e2.ename,e2.deptno,d2.dname,decode(s2.grade,1,'一级',2,'二级',3,'三级',4,'四级',5,'五级') "sallevel"
from emp e1,dept d1,emp e2,dept d2，salgrade s1,salgrade s2
where e1.deptno = d1.deptno 
and e1.sal between s1.losal and s1.hisal 
and e1.mgr = e2.empno 
and e2.deptno =d2.deptno 
and e2.sal between s2.losal and s2.hisal;


----#####################################外连接
----1.查询员工编号，姓名，领导编号，领导姓名，包括没领导的
--left join...on 方式
select e1.empno,e1.ename,e2.empno,e2.ename from emp e1,emp e2 where e1.mgr = e2.empno;  --（不包括没领导的，错误）
select e1.empno,e1.ename,e2.empno,e2.ename from emp e1 left join emp e2 on e1.mgr = e2.empno;  --（包括没领导的）

--oracle的（+）方式    :以没写加号的一边为准
select e1.empno,e1.ename,e2.empno,e2.ename from emp e1,emp e2 where e1.mgr = e2.empno(+);

----2.查询出所有部门信息（包括没员工的部门）及部门下的员工信息
select * from emp,dept where emp.deptno(+) = dept.deptno;


----####################################################子查询
--1.查询比员工7654工资高，同时从事和7788的工作一样的员工empno =7788
select * from emp where sal > (select sal from emp where empno =7654)
and job = (select job from emp where empno =7788);

--2.查询每个部门最低工资及最低工资的部门名称和员工信息
select * from (select min(sal) minsal,deptno from emp group by deptno) e1,emp,dept
where emp.deptno =dept.deptno and dept.deptno = e1.deptno  and emp.sal=e1.minsal;

select minsal,emp.deptno,dname,ename from (select min(sal) minsal,deptno from emp group by deptno) e1,emp,dept
where emp.deptno =dept.deptno and dept.deptno = e1.deptno  and emp.sal=e1.minsal;


----#########################课堂练习
--1.找到员工表中工资最高的前三名
 select rownum, e.* from (select emp.* from emp order by sal desc) e where rownum <= 3;

--2.找到员工表中薪水大于本部门平均工资的所有员工
select emp.empno,emp.ename,e.deptno,e.averagesal,emp.sal from (select avg(sal) averagesal,deptno from emp group by deptno) e,emp
where emp.deptno = e.deptno and emp.sal > e.averagesal
 
--3.统计每年入职的员工个数
select count(*),to_char(hiredate,'yyyy') from emp group by to_char(hiredate,'yyyy');
