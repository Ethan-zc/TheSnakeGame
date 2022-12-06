CREATE TABLE game (
    gameid    BIGINT NOT NULL,
    starttime DATETIME,
    endtime   DATETIME
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
    score       VARCHAR(50),
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