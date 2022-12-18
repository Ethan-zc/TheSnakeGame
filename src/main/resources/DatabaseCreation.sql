CREATE TABLE game (
	gameid INTEGER PRIMARY KEY AUTOINCREMENT,
	starttime DATETIME
);

CREATE TABLE acc (
	accid INTEGER PRIMARY KEY AUTOINCREMENT, 
	accname VARCHAR(30),
	pwd VARCHAR(50)
);


CREATE TABLE acc_game (
	accid INTEGER NOT NULL,
	gameid INTEGER NOT NULL,
	score BIGINT NOT NULL,
	FOREIGN KEY (accid) REFERENCES acc(accid),
	FOREIGN KEY (gameid) REFERENCES game(gameid)
);

INSERT INTO `acc` (`accname`, `pwd`) VALUES ('zichenyang', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `acc` (`accname`, `pwd`) VALUES ('zhangnan', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `acc` (`accname`, `pwd`) VALUES ('pochita', 'e10adc3949ba59abbe56e057f20f883e');

INSERT INTO `game` (`starttime`) VALUES ('2020-09-14 23:18:17');
INSERT INTO `game` (`starttime`) VALUES ('2022-12-14 23:18:17');

INSERT INTO `acc_game` (`score`, `accid`, `gameid`) VALUES ('123456', '1', '1');
INSERT INTO `acc_game` (`score`, `accid`, `gameid`) VALUES ('23441', '2', '2');
INSERT INTO `acc_game` (`score`, `accid`, `gameid`) VALUES ('12314123', '3', '1');
INSERT INTO `acc_game` (`score`, `accid`, `gameid`) VALUES ('1312', '1', '2');
INSERT INTO `acc_game` (`score`, `accid`, `gameid`) VALUES ('1312', '3', '2');