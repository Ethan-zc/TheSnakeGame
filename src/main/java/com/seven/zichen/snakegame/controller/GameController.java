package com.seven.zichen.snakegame.controller;

import com.seven.zichen.snakegame.entry.GameEntry;
import com.seven.zichen.snakegame.games_handler.GH_Manager;
import com.seven.zichen.snakegame.service.AccountService;
import com.seven.zichen.snakegame.service.GameService;
import com.seven.zichen.snakegame.service.Impl.GameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/printhist", method = RequestMethod.POST)
    public String printHist(@RequestParam String X,
                            @RequestParam String Y) {

        System.out.println("X:" + X);
        System.out.println("Y: " + Y);
        System.out.println("----------------------");

        return "Received!";
    }

    @RequestMapping(value = "/addgame", method = RequestMethod.POST)
    public String addGame(@RequestParam String starttime) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:SS zzz yyyy", Locale.ENGLISH);
        Date date = formatter.parse(starttime);
        gameService.addGame(date);
        int id = gameService.selectNewId();
        return id + "";
    }

    @RequestMapping(value = "/addscore", method = RequestMethod.POST)
    public void addScore(@RequestParam String userName,
                         @RequestParam int gameId,
                         @RequestParam int score) {
        int userId = accountService.getUserIdByName(userName);
        gameService.addScore(userId, gameId, score);
    }

    @RequestMapping(value = "/testing", method = RequestMethod.GET)
    public int testing() {
        return gameService.selectNewId();
    }

    @RequestMapping(value = "/allgame", method = RequestMethod.GET)
    public List<GameEntry> allgame() {
        return gameService.getAllGames();
    }


//    @RequestMapping(value = "/newgame", method = RequestMethod.GET)
//    public void testing() throws IOException {
//        System.out.println("Server received start game request!");
//    }

}
