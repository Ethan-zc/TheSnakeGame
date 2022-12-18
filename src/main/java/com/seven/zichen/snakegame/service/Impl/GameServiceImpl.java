package com.seven.zichen.snakegame.service.Impl;

import com.seven.zichen.snakegame.dao.GameMapper;
import com.seven.zichen.snakegame.entry.GameEntry;
import com.seven.zichen.snakegame.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    protected GameMapper gameMapper;

    @Override
    public void addHistory(int userId, int gameId, int score) {
        gameMapper.addScore(userId, gameId, score);
    }

    @Override
    public int addGame(Date startTime) {
        gameMapper.addGame(startTime);
        return gameMapper.getIdByTime(startTime);
    }

    @Override
    public void addScore(int userId, int gameId, int score) {
        gameMapper.addScore(userId, gameId, score);
    }

    @Override
    public int selectNewId() {
        return gameMapper.getNewId();
    }

    @Override
    public List<GameEntry> getAllGames() {
        return gameMapper.getAllGames();
    }
}