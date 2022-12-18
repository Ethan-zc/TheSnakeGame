package com.seven.zichen.snakegame.dao;

import com.seven.zichen.snakegame.entry.GameEntry;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface GameMapper {

    @Insert("INSERT INTO game (starttime) " +
            "VALUES (#{startTime})")
    public void addGame(Date startTime);

    @Insert("INSERT INTO acc_game(accid, gameid, score) " +
            "VALUES (#{accid}, #{gameid}, #{score})")
    public void addScore(int accid, int gameid, int score);

    @Select("SELECT starttime FROM game WHERE starttime = #{time}")
    public int getIdByTime(Date time);

    @Select("SELECT MAX(gameid) FROM game")
    public int getNewId();

    @Select("SELECT * FROM game")
    public List<GameEntry> getAllGames();
}
