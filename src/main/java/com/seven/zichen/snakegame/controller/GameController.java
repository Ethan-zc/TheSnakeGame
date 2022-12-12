package com.seven.zichen.snakegame.controller;

import com.seven.zichen.snakegame.entry.GameEntry;
import com.seven.zichen.snakegame.service.GameService;
import com.seven.zichen.snakegame.service.Impl.GameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @RequestMapping(value = "/printhist", method = RequestMethod.POST)
    public String printHist(@RequestParam String X,
                            @RequestParam String Y) {

        System.out.println("X:" + X);
        System.out.println("Y: " + Y);
        System.out.println("----------------------");

        return "Received!";
    }

    @RequestMapping(value = "/addhist", method = RequestMethod.POST)
    public void addHistory(@RequestParam String user1,
                           @RequestParam int score1,
                           @RequestParam String user2,
                           @RequestParam int score2,
                           @RequestParam String user3,
                           @RequestParam int score3,
                           @RequestParam String user4,
                           @RequestParam int score4
                           ) {

    }

    @RequestMapping(value = "/addgame", method = RequestMethod.POST)
    public void addGame(@RequestParam Date starttime,
                        @RequestParam Date endtime) {

        gameService.addGame(starttime, endtime);
    }

}
