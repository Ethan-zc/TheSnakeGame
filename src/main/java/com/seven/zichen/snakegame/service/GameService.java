package com.seven.zichen.snakegame.service;

import java.util.Date;

public interface GameService {

    void addHistory(int userId, int gameId, int score);

    void addGame(Date startTime, Date endTime);
}
