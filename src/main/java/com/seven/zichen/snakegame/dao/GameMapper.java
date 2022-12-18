package com.seven.zichen.snakegame.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Mapper
@Repository
public interface GameMapper {

    @Insert("INSERT INTO game (starttime, endtime) " +
            "VALUES (#{startTime})")
    public void addGame(Date startTime);

    @Insert("INSERT INTO acc_game(accid, gameid, score) " +
            "VALUES (#{accid}, #{gameid}, #{score})")
    public void addScore(int accid, int gameid, int score);

}
