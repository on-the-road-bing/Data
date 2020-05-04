---###################################################一、数据库基本查询select、distinct、nvl、||、concat
select * from emp;

--查询emp中的信息，并用中文字段重命名
select empno "员工编号"，ename "姓名",job "职位"，mgr "领导编号"，hiredate "入职日期",sal "工资"，comm "奖金",deptno "部门编号"from emp;   --只有别名用双引号

--查询emp表中job信息，并去除重复信息
select distinct(job) from emp;

--查询emp表中员工的全年的工资总和(sal总和）
select ename,sal*12 from emp;

--查询emp中员工的全年收入总和（sal+comm)
select ename,sal*12+comm from emp;--数据有问题，有的员工comm为空值
select ename,sal*12+nvl(comm,0) from emp;

--查询emp表中员工编号，姓名，输出格式为：编号：xxx，姓名：xxx
-----concat拼接方式
select concat(concat('员工编号：',empno),concat(',姓名：',ename）) from emp;

--oracle的\\方式
select '编号:'||empno||'，姓名:'||ename from emp;


-----################################################二、条件查询：where >= ,<= ,!= ,<>
----查询工资大于1500的员工
select * from emp where sal>=1500;

----查询工资大于1500并且有奖金的员工
select * from emp where sal>=1500 and comm is not null;

----查询工资大于1500或者有奖金的员工
select * from emp where sal>=1500 or comm is  not null;

----查询工资大于1500并且没奖金的员工
select * from emp where sal>=1500 and comm is  null;

----查询姓名为smith的员工
select * from emp where ename ='smith'; 




-----################################################三、范围查询:between...and,  in,     is null,     is not null
--1.>=,<=方式
select *from emp where sal>=1500 and sal<=3000;

--2.between and 方式
select * from emp where sal between 1500 and 3000; 

--查询 1981-1-1到1981-12-21号入职的员工 
select * from emp where hiredate between to_date('1981-1-1','yyyy-mm-dd') and to_date('1981-12-21','yyyy-mm-dd');

--查询员工编号是7369,7654,7566的员工
-----1.or方式
select * from emp where empno=7369 or empno =7654 or empno = 7566;
-----2.in方式
select * from emp where empno in(7369,7654,7566);

--查询姓名是'smith,allen,ward'的学生           字符串注意大小写

select * from emp where ename in('SMITH','ALLEN','WARD');




------##############################################四、模糊查询：like   '%'
--查询所有员工姓名中第二个字符有'M'的员工
select * from emp where ename like '_M%';

--查询员工姓名中有'M"的员工
select * from emp where ename like '%M%';

--查询员工编号不是7369的员工信息
----1.<>方式
select * from emp where empno <> 7369;
----2.!=方式
select * from emp where empno != 7369;



----################################################五、排序：order by
--查询员工工资进行降序排序（默认升序）   
select ename,sal from emp order by sal desc;

--查询员工奖金并做降序排序（关于nulls first/nulls last),nulls first默认在前头
select ename,comm from emp order by comm desc nulls last;

--查询员工工资做降序排序并且其中奖金部分是升序排序
select ename,sal,comm from emp order by sal desc,comm asc;



-----##############################################六、日期函数：sysdate、add_months()、months_between()
--查询系统时间                关键字sysdate
select sysdate from dual;                       --dual为伪表

--查询员工进入公司周数
select ename,hiredate,(sysdate-hiredate)/7 from emp;

--查询员工进入公司月数        关键字months_between
select ename,hiredate,months_between(sysdate,hiredate) from emp;

--求出员工入职三个月后的日期  关键字add_months
select ename,hiredate,add_months(hiredate,3) from emp;




----###############################################七、单行函数一、转换函数  :to_char()   ,  to_date()   , to_number()
--------1.（日期转字符串，字符串转日期）

--将系统时间显示为yyyy-mm-dd hh:mi:ss      关键字to_char
select to_char(sysdate,'yyyy-mm-dd hh:mi:ss') from dual;

--显示成年月日
select to_char(sysdate,'yyyy')||'年'||to_char(sysdate,'mm')||'月'||to_char(sysdate,'dd')||'日' from dual;

--将字符串'1981-1-2'转换成日期类型          关键字to_date
select to_date('1981-1-2','yyyy-mm-dd') from dual;

---------2.(字符串转数值，数值转字符串)
select to_number('99') from dual;   --字符串转数值
select to_char(99) from dual;       --数值转字符串


---#################################################单行函数二、通用函数
--1.空值的处理函数     nvl
select ename,nvl(comm,0) from emp;

--2.nvl2(判断值,空返回值，非空返回值)
select nvl2(null,1,2) from dual;
select ename,nvl2(comm,1,2) from emp;


----条件表达式
--1. 查询员工的job内容并转成中文显示
-----decode方式
select ename,decode(job,'CLERK','柜员'，'SALESMAN','销售'，'MANAGER','管理'，'其他') from emp;

--2. case..when..then..end方式
select ename,case job when 'CLERK' then '柜员'
when 'SALESMAN' then '销售'
  when 'MANAGER'then '管理'
    else 
      '其他'
      end from emp;



----#################################################八、多行函数一：count() ,  sum(),  avg(),  max(),  min()
--1.查询所有员工记录数              （表格中记录的行数）
select count(*) from emp;

--2.查询佣金总数（如何查询某个字段的而总数量）
select sum(comm) from emp;

--3.查询最低工资          关键字min
select min(sal) from emp;
--4.查询最高工资          关键字max
select max(sal) from emp;

--5.查询平均工资          关键字avg
select avg(sal) from emp;

--6.查询20号部门的员工工资总和
select sum(sal) from emp where deptno =20;


----###################################################多行函数二：分组查询（group  by）
--1.查询部门编号及人数  ----- 分组查询关键字group by
select deptno,count(*) from emp group by deptno;

--2.查询每个部门编号及平均工资
select deptno,avg(sal) from emp group by deptno;

--3.查询部门名称，部门编号，平均工资
select dname,emp.deptno,avg(sal) from emp,dept where emp.deptno=dept.deptno group by emp.deptno,dname;


-----###################################################多行函数三：过滤（having）having
--4.查询部门人数大于5人的部门
select deptno,count(*) from emp group by deptno having count(*)>5; 

--5.查询部门编号，部门名称，平均工资且平均工资大于2000
select emp.deptno,dname,avg(sal) from dept,emp where emp.deptno =dept.deptno group by  emp.deptno,dname having avg(sal)>=2000;




