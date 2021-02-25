select * from tab;
select * from seq;
select * from jspmodel2;
--drop sequence seq¿Ã∏ß;

create table jspmodel2(
	board_num number,
	board_name varchar2(20),
	board_pass varchar2(15),
	board_subject varchar2(50),
	board_content varchar2(2000),
	board_file varchar2(50),
	board_re_ref number,
	board_re_lev number,
	board_re_seq number,
	board_readcount number,
	board_date timestamp,
	primary key(board_num)
);

create sequence jspmodel2_seq
start with 1
increment by 1
nocache;
