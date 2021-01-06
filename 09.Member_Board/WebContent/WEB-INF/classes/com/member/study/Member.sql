--회원관리
--테이블 생성
create TABLE boardMember(
    Member_id VARCHAR2(15) PRIMARY key not null,
    Member_pw VARCHAR2(15),
    Member_name VARCHAR2(15),
    Member_age number,
    Member_gender VARCHAR2(5),
    Member_email VARCHAR2(30)
);

--테이블 제거
drop TABLE boardMember;

--자동증가값 설정 : b_num -> b_num_seq 변수
create SEQUENCE b_num_seq start with 1;
--자동증가값 삭제(해제)
drop SEQUENCE b_num_seq;

--전체레코드 검색
select * from boardMember;
select count(*) from boardMember;

--임의의 레코드 삽입
insert into boardMember
VALUES (B_NUM_SEQ.nextval, 'subject', 'pwd', 'content', 'writer', sysdate, 0);

--전체 레코드 삭제
delete from boardMember;

