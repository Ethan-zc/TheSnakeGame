package com.seven.zichen.snakegame.service;

import com.seven.zichen.snakegame.entry.GameEntry;

import java.util.Date;
import java.util.List;

public interface GameService {

    void addHistory(int userId, int gameId, int score);

    int addGame(Date startTime);

    void addScore(int userId, int gameId, int score);

    int selectNewId();

    List<GameEntry> getAllGames();
}