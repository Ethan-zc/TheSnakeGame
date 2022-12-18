CREATE TABLE game (
    gameid    BIGINT NOT NULL,
    starttime DATETIME,
);

ALTER TABLE game ADD CONSTRAINT game_pk PRIMARY KEY ( gameid );
ALTER TABLE game MODIFY gameid BIGINT AUTO_INCREMENT;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE acc (
    accid   BIGINT NOT NULL,
    accname VARCHAR(30),
    pwd      VARCHAR(50)
);

ALTER TABLE acc ADD CONSTRAINT acc_pk PRIMARY KEY ( accid );
ALTER TABLE acc MODIFY accid BIGINT AUTO_INCREMENT;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE acc_game (
    score BIGINT,
    accid BIGINT NOT NULL,
    gameid BIGINT NOT NULL
);

ALTER TABLE acc_game ADD CONSTRAINT acc_game_pk PRIMARY KEY ( accid,
															  gameid );

ALTER TABLE acc_game
    ADD CONSTRAINT game_accgame_fk FOREIGN KEY ( gameid )
        REFERENCES game ( gameid );

ALTER TABLE acc_game
    ADD CONSTRAINT acc_accgame_fk FOREIGN KEY ( accid )
        REFERENCES acc ( accid );

INSERT INTO `try`.`acc` (`accname`, `pwd`) VALUES ('zichenyang', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `try`.`acc` (`accname`, `pwd`) VALUES ('zhangnan', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `try`.`acc` (`accname`, `pwd`) VALUES ('pochita', 'e10adc3949ba59abbe56e057f20f883e');

INSERT INTO `try`.`game` (`starttime`, `endtime`) VALUES ('2020-11-01 00:00:00');
INSERT INTO `try`.`game` (`starttime`, `endtime`) VALUES ('2020-12-01 00:00:00');

INSERT INTO `try`.`acc_game` (`score`, `accid`, `gameid`) VALUES ('123456', '1', '1');
INSERT INTO `try`.`acc_game` (`score`, `accid`, `gameid`) VALUES ('23441', '2', '2');
INSERT INTO `try`.`acc_game` (`score`, `accid`, `gameid`) VALUES ('12314123', '3', '1');
INSERT INTO `try`.`acc_game` (`score`, `accid`, `gameid`) VALUES ('1312', '1', '2');
INSERT INTO `try`.`acc_game` (`score`, `accid`, `gameid`) VALUES ('1312', '3', '2');
