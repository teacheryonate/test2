



CREATE TABLE tbl_menu(
uri VARCHAR2(100),
fullpkg VARCHAR2(100) NOT NULL,
CONSTRAINT pk_menu_uri PRIMARY KEY(uri)
);

INSERT INTO tbl_menu VALUES ('/board/list.do','kr.co.command.Board_ListCommand')
INSERT INTO tbl_menu VALUES ('/board/insertui.do','kr.co.command.Board_InsertUICommand')
INSERT INTO tbl_menu VALUES ('/board/insert.do','kr.co.command.Board_InsertCommand')
INSERT INTO tbl_menu VALUES ('/board/read.do','kr.co.command.Board_ReadCommand')
INSERT INTO tbl_menu VALUES ('/board/updateui.do','kr.co.command.Board_UpdateUICommand')
INSERT INTO tbl_menu VALUES ('/board/update.do','kr.co.command.Board_UpdateCommand')
INSERT INTO tbl_menu VALUES ('/board/delete.do','kr.co.command.Board_DeleteCommand')
INSERT INTO tbl_menu VALUES ('/board/replyui.do','kr.co.command.Board_ReplyUICommand')
INSERT INTO tbl_menu VALUES ('/board/reply.do','kr.co.command.Board_ReplyCommand')



SELECT * FROM tbl_board WHERE UPPER(title) LIKE UPPER('%Hello%')





SELECT COUNT(num) FROM tbl_board

SELECT COUNT(num) FROM tbl_board WHERE title LIKE '%%'

SELECT * FROM 
(SELECT rownum rnum, num, author, title, TO_CHAR(writeday, 'yyyy-mm-dd') writeday, readCnt, repIndent FROM
(SELECT * FROM tbl_board WHERE title LIKE '%hello%' ORDER BY repRoot DESC, repStep ASC))
WHERE rnum BETWEEN 1 AND 10






select * from tbl_board ORDER BY repRoot DESC, repStep ASC


SELECT * FROM 
(SELECT rownum rnum, num, author, title, TO_CHAR(writeday, 'yyyy-mm-dd') writeday, readcnt, repIndent 
FROM 
(SELECT * FROM tbl_board ORDER BY repRoot DESC, repStep ASC))
WHERE
rnum BETWEEN 21 AND 30













delete from tbl_board
commit










CREATE SEQUENCE seq_board_num
START WITH 100
INCREMENT BY 1;
















CREATE TABLE tbl_board(
num NUMBER,
author VARCHAR2(15) NOT NULL,
title VARCHAR2(45) NOT NULL,
content VARCHAR2(3000) NOT NULL,
pw NUMBER NOT NULL,
writeday DATE DEFAULT SYSDATE,
readcnt NUMBER DEFAULT 0,

repRoot NUMBER, 
repStep NUMBER,
repIndent NUMBER,
CONSTRAINT pk_board_num PRIMARY KEY(num)
)