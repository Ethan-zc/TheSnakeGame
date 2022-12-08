package com.seven.zichen.snakegame.controller;

import com.seven.zichen.snakegame.entry.AccountEntry;
import com.seven.zichen.snakegame.entry.ScoreEntry;
import com.seven.zichen.snakegame.service.AccountService;
import io.swagger.annotations.Api;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@Api(tags = "Account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam String accname, @RequestParam String pwd) {
        pwd = DigestUtils.md5Hex(pwd);
        if (accname == null || accname.equals("") || pwd.equals("")) {
            return "Account Name or password could not be empty. ";
        } else {
            accountService.createNewAccount(accname, pwd);
            return "Success!";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String accname, @RequestParam String pwd) {
        AccountEntry foundAcc = accountService.getAccountByAccname(accname);
        pwd = DigestUtils.md5Hex(pwd);
        if (foundAcc == null || !foundAcc.getPwd().equals(pwd)) {
            return "Failed";
        } else {
            return accname;
        }
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void testing() {
        System.out.println(accountService.getAllAccounts());
    }

    @RequestMapping(value = "/getLeaderBoard", method = RequestMethod.GET)
    public List<ScoreEntry> getLeaderBoard() {
        return accountService.getScore();
    }

}
