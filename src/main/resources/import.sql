INSERT INTO USERS VALUES (1, 'user1@example.com', null, 'user1', 'password1@#$', 'USER');
INSERT INTO USERS VALUES (2, 'user2@example.com', null, 'user2', 'password2@#$', 'USER');
INSERT INTO USERS VALUES (3, 'user3@example.com', null, 'user3', 'password3@#$', 'USER');
INSERT INTO USERS VALUES (4, 'user4@example.com', null, 'user4', 'password4@#$', 'USER');
INSERT INTO USERS VALUES (5, 'user5@example.com', null, 'user5', 'password5@#$', 'USER');
INSERT INTO USERS VALUES (6, 'admin1@example.com', null, 'admin1', '$2a$10$4v4ETGTnjZjDw674aiSmxuRAJHhaERZzp6At8ER8yk6svHttlAjoK', 'ADMIN');
INSERT INTO USERS VALUES (7, 'testuser1@example.com', null, 'testuser1', '$2a$10$rv88wx/1N8VVrjmE/uUA8e2lTVzxjHthiBuXpiQAmzeKO08P99pTy', 'USER');
INSERT INTO USERS VALUES (8, 'testuser2@example.com', null, 'testuser2', '$2a$10$rv88wx/1N8VVrjmE/uUA8e2lTVzxjHthiBuXpiQAmzeKO08P99pTy', 'USER');
INSERT INTO USERS VALUES (9, 'testuser3@example.com', null, 'testuser3', '$2a$10$rv88wx/1N8VVrjmE/uUA8e2lTVzxjHthiBuXpiQAmzeKO08P99pTy', 'USER');



INSERT INTO CATEGORY VALUES(1, 100, '여성의류');
INSERT INTO CATEGORY VALUES(2, 200, '남성의류');
INSERT INTO CATEGORY VALUES(3, 300, '신발');
INSERT INTO CATEGORY VALUES(4, 400, '가방');
INSERT INTO CATEGORY VALUES(5, 500, '시계/주얼리');
INSERT INTO CATEGORY VALUES(6, 600, '패션 액세서리');
INSERT INTO CATEGORY VALUES(7, 700, '디지털/가전');
INSERT INTO CATEGORY VALUES(8, 800, '스포츠/레저');

INSERT INTO PRODUCT VALUES(1, '2022-11-07T15:30', '2022-11-08T15:30', 3, TRUE, '신발입니다.', TRUE, 'jfiejdkfiejf', FALSE, 50000, 1, TRUE, '신발 팜니다', TRUE, '1');
INSERT INTO PRODUCT VALUES(2, '2022-12-01T10:00', '2022-12-05T18:00', 2, FALSE, '남성 코트입니다.', FALSE, 'djoedfjoifej', TRUE, 80000, 2, TRUE, '남성 코트 구매합니다', TRUE, '2');
INSERT INTO PRODUCT VALUES(3, '2022-10-20T14:00', '2022-10-27T14:00', 3, TRUE, '여성 블라우스입니다.', TRUE, 'djoedfjoifej', FALSE, 25000, 1, FALSE, '여성 블라우스 구매합니다', TRUE, '3');
INSERT INTO PRODUCT VALUES(4, '2022-11-15T08:00', '2022-11-20T20:00', 8, FALSE, '등산용 배낭입니다.', TRUE, 'eifnefjienf', TRUE, 45000, 4, FALSE, '등산용 배낭 팝니다', FALSE, '4');
INSERT INTO PRODUCT VALUES(5, '2022-12-10T12:00', '2022-12-15T20:00', 4, TRUE, '가죽 여행용 가방입니다.', FALSE, 'djoedfjoifej', TRUE, 120000, 4, FALSE, '여행용 가방 팝니다', TRUE, '5');
INSERT INTO PRODUCT VALUES(6, '2022-09-01T08:00', '2022-09-08T20:00', 3, TRUE, '노트북입니다.', TRUE, 'dlkjfnviwjef', TRUE, 1500000, 7, TRUE, '노트북 구매합니다', FALSE, '1');
INSERT INTO PRODUCT VALUES(7, '2022-12-01T10:00', '2022-12-15T10:00', 5, TRUE, '팔찌입니다.', FALSE, 'oieruyxcvnm', TRUE, 20000, 5, TRUE, '팔찌 팜니다', TRUE, '1');
INSERT INTO PRODUCT VALUES(8, '2022-10-10T09:00', '2022-10-20T09:00', 4, FALSE, '핸드백입니다.', FALSE, 'oieruyxcvnm', TRUE, 80000, 4, FALSE, '핸드백 팜니다', FALSE, '2');
INSERT INTO PRODUCT VALUES(9, '2022-09-23T14:00', '2022-10-23T14:00', 8, TRUE, '수영복입니다.', TRUE, 'kdisfsksdfsf', FALSE, 50000, 1, TRUE, '수영복 팜니다', TRUE, '2');
INSERT INTO PRODUCT VALUES(10, '2022-08-14T15:00', '2022-09-14T15:00', 7, FALSE, '마우스입니다.', TRUE, 'oieruyxcvnm', FALSE, 25000, 3, FALSE, '마우스 팜니다', FALSE, '3');
INSERT INTO PRODUCT VALUES(11, '2022-11-10T12:00', '2022-11-25T12:00', 1, TRUE, '원피스입니다.', FALSE, 'oieruyxcvnm', TRUE, 50000, 1, TRUE, '원피스 팜니다', TRUE, '3');
INSERT INTO PRODUCT VALUES(12, '2022-07-20T11:00', '2022-08-10T11:00', 2, FALSE, '캐주얼셔츠입니다.', FALSE, 'oieruyxcvnm', TRUE, 30000, 2, TRUE, '셔츠 팜니다', TRUE, '4');
INSERT INTO PRODUCT VALUES(13, '2022-11-17T09:00', '2022-11-27T09:00', 6, TRUE, '귀걸이입니다.', FALSE, 'oieruyxcvnm', TRUE, 15000, 5, FALSE, '귀걸이 팜니다', TRUE, '5');
INSERT INTO PRODUCT VALUES(14, '2022-10-05T13:00', '2022-11-05T13:00', 8, TRUE, '헬스 용품입니다.', TRUE, 'sdfsdfsdfsdfs', FALSE, 60000, 8, TRUE, '헬스용품 팜니다', TRUE, '1');
INSERT INTO PRODUCT VALUES(15, '2022-09-03T10:00', '2022-09-20T10:00', 3, TRUE, '구두입니다.', TRUE, 'fdgdgsfdhgr', FALSE, 70000, 3, TRUE, '구두 팜니다', FALSE, '2');