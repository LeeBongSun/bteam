--회원관리
--테이블 생성
create TABLE memberBoard(
    Board_num number PRIMARY key not null,
    Board_id VARCHAR2(15),
    Board_subject VARCHAR2(50),
    Board_content VARCHAR2(2000),
    Board_file VARCHAR2(20),
    Board_re_ref number,
    Board_re_lev number,
    Board_re_seq number,
    Board_readcount number,
    Board_date date
);

--제약조건 추가
alter table memberBoard
    add constraint pk_board_id foreign key(Board_id)
        references boardMember(Memeber_id);

--테이블 제거
drop TABLE memberBoard;

--자동증가값 설정 : b_num -> b_num_seq 변수
create SEQUENCE mb_num_seq start with 1;
--자동증가값 삭제(해제)
drop SEQUENCE mb_num_seq;

--전체레코드 검색
select * from memberBoard;

--임의의 레코드 삽입
insert into memberBoard
VALUES (MB_NUM_SEQ.nextval, 'subject', 'pwd', 'content', 'writer', sysdate, 0);

--전체 레코드 삭제
delete from memberBoard;

