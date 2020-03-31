drop database o2b2;

create database o2b2;

show databases;

use o2b2;

show tables;

-- 회원정보(profile)테이블------------------------------------------------------
create table profile(
	SerialNum varchar(30) primary key,
    phoneNum varchar(30) not null,
    password1 varchar(30) not null
);
desc profile;

-- 테이블 속성 변경 
ALTER table profile modify phonenum varchar(30);
ALTER TABLE profile modify serialnum varchar (30);

-- 데이터 삽입
insert into profile(serialnum, phonenum, password1) values(1,'01047360754','1');

insert into profile(serialnum, phonenum, password1) values(2,'01030346810','1');
insert into profile(serialnum, phonenum, password1) values(3,'01037997288','1');
insert into profile(serialnum, phonenum, password1) values(4,'01022137511','1');
insert into profile(serialnum, phonenum, password1) values(5,'01072807465','1');

-- order by xxx asc (오름차순 정렬)
select * from profile order by serialnum asc;
-- 시리얼 번호로 원하는 값 찾기
select * from profile where serialnum=3;
-- 데이터 삭제
delete from profile  where password1 = 'asd4567';

delete from profile where phonenum = '01047360754' and password1= '1';

alter table profile auto_increment=1;
set @count =0;
update profile set serialnum = @count:=count+1; 



drop table profile;
drop table realstudytime;

-- 실제 공부시간(RealStudyTime) 테이블-------------------------------------------------------
create table RealStudyTime(
	SerialNum varchar(30) not null,
    foreign key(SerialNum) references profile(SerialNum) on delete cascade on update cascade,
    studytime varchar(30) not null,
    date date not null,
    subject int(10) not null
);
desc RealStudyTime;

-- 테이블속성변경 
ALTER table RealStudyTime modify SerialNum int not null;
alter table  realstudytime add constraint profile foreign key (Serialnum) references profile(Serialnum) on delete cascade;

-- 실제 학습시간 데이터 넣기( serialNum 칼럼은 profile 테이블에 존재하는 값을 넣어야 한다.
insert into RealStudyTime values(1, "0.08", "2020-03-01", "2");

insert into RealStudyTime values(2, "8", "2020-03-01", "2");
insert into RealStudyTime values(2, "10", "2020-03-02", "4");
insert into RealStudyTime values(2, "5", "2020-03-03", "3");
insert into RealStudyTime values(2, "9", "2020-03-04", "2");
insert into RealStudyTime values(2, "6", "2020-03-05", "2");
insert into RealStudyTime values(2, "12", "2020-03-06", "3");
insert into RealStudyTime values(2, "4", "2020-03-07", "4");

insert into RealStudyTime values(3, "5", "2020-03-01", "2");
insert into RealStudyTime values(3, "8", "2020-03-02", "3");
insert into RealStudyTime values(3, "9", "2020-03-03", "4");
insert into RealStudyTime values(3, "3", "2020-03-04", "2");
insert into RealStudyTime values(3, "6", "2020-03-05", "4");
insert into RealStudyTime values(3, "11", "2020-03-06", "5");
insert into RealStudyTime values(3, "5", "2020-03-07", "2");

insert into RealStudyTime values(4, "6", "2020-03-01", "5");
insert into RealStudyTime values(4, "7", "2020-03-02", "2");
insert into RealStudyTime values(4, "9", "2020-03-03", "4");
insert into RealStudyTime values(4, "10", "2020-03-04", "2");
insert into RealStudyTime values(4, "7", "2020-03-05", "6");
insert into RealStudyTime values(4, "8", "2020-03-06", "4");
insert into RealStudyTime values(4, "5", "2020-03-07", "2");

insert into RealStudyTime values(5, "7", "2020-03-01", "4");
insert into RealStudyTime values(5, "2", "2020-03-02", "5");
insert into RealStudyTime values(5, "9", "2020-03-03", "4");
insert into RealStudyTime values(5, "7", "2020-03-04", "2");
insert into RealStudyTime values(5, "5", "2020-03-05", "3");
insert into RealStudyTime values(5, "9", "2020-03-06", "4");
insert into RealStudyTime values(5, "4", "2020-03-07", "2");

 -- 검색
select * from realstudytime order by serialnum asc;

Delete from RealStudyTime where SerialNum = "1" and studytime= "0.08" and date= "2020-03-01" and subject= "2";

delete from realstudytime where studytime=0.08;

show tables;

drop table ScheduleStudyTime;

-- 스케줄 공부시간(ScheduleStudyTime) 테이블-------------------------------------------------
create table ScheduleStudyTime(
	SerialNum varchar(30) not null,
    foreign key(SerialNum) references profile(SerialNum) on delete cascade on update cascade,
    studytime varchar(30) not null,
    date date not null,
    subject int(10) not null
);
desc ScheduleStudyTime;

alter table schedulestudytime add constraint profile foreign key (serialnum) references profile(serialnum) on delete cascade;

 -- 스케쥴 공부시간 데이터 삭제
delete from ScheduleStudyTime where serialnum = "6";


-- 스케쥴 공부시간 데이터 삽입

insert into ScheduleStudyTime values(2, "4", "2020-03-01", "2");
insert into ScheduleStudyTime values(2, "5", "2020-03-02", "4");
insert into ScheduleStudyTime values(2, "3", "2020-03-03", "3");
insert into ScheduleStudyTime values(2, "2", "2020-03-04", "2");
insert into ScheduleStudyTime values(2, "4", "2020-03-05", "2");
insert into ScheduleStudyTime values(2, "2", "2020-03-06", "3");
insert into ScheduleStudyTime values(2, "3", "2020-03-07", "4");

insert into ScheduleStudyTime values(3, "2", "2020-03-01", "2");
insert into ScheduleStudyTime values(3, "3", "2020-03-02", "3");
insert into ScheduleStudyTime values(3, "2", "2020-03-03", "4");
insert into ScheduleStudyTime values(3, "5", "2020-03-04", "2");
insert into ScheduleStudyTime values(3, "7", "2020-03-05", "4");
insert into ScheduleStudyTime values(3, "6", "2020-03-06", "5");
insert into ScheduleStudyTime values(3, "2", "2020-03-07", "2");

insert into ScheduleStudyTime values(4, "2", "2020-03-01", "5");
insert into ScheduleStudyTime values(4, "3", "2020-03-02", "2");
insert into ScheduleStudyTime values(4, "2", "2020-03-03", "4");
insert into ScheduleStudyTime values(4, "5", "2020-03-04", "2");
insert into ScheduleStudyTime values(4, "4", "2020-03-05", "6");
insert into ScheduleStudyTime values(4, "3", "2020-03-06", "4");
insert into ScheduleStudyTime values(4, "2", "2020-03-07", "2");

insert into ScheduleStudyTime values(5, "2", "2020-03-01", "4");
insert into ScheduleStudyTime values(5, "4", "2020-03-02", "5");
insert into ScheduleStudyTime values(5, "4", "2020-03-03", "4");
insert into ScheduleStudyTime values(5, "3", "2020-03-04", "2");
insert into ScheduleStudyTime values(5, "6", "2020-03-05", "3");
insert into ScheduleStudyTime values(5, "4", "2020-03-06", "4");
insert into ScheduleStudyTime values(5, "2", "2020-03-07", "2");

 -- 검색
select *from scheduleStudytime order by serialnum asc;


-- 관리자 테이블
create table dohun_password(
	num varchar(30),
    passnum varchar(30)
);
desc dohun_password;


























