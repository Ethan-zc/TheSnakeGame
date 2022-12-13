package com.seven.zichen.snakegame.service.Impl;

import com.seven.zichen.snakegame.dao.GameMapper;
import com.seven.zichen.snakegame.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    protected GameMapper gameMapper;

    @Override
    public void addHistory(int userId, int gameId, int score) {
        gameMapper.addScore(userId, gameId, score);
    }

    @Override
    public void addGame(Date startTime, Date endTime) {
        gameMapper.addGame(startTime, endTime);
    }

    @Override
    public void addScore(int userId, int gameId, int score) {
        gameMapper.addScore(userId, gameId, score);
    }

}
