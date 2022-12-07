package com.seven.zichen.snakegame.controller;

import com.seven.zichen.snakegame.entry.GameEntry;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;

@RestController
@RequestMapping("/game")
public class GameController {

    @RequestMapping(value = "/printhist", method = RequestMethod.POST)
    public String printHist(@RequestParam String X,
                            @RequestParam String Y) {

        System.out.println("X:" + X);
        System.out.println("Y: " + Y);
        System.out.println("----------------------");

        return "Received!";
    }

}
