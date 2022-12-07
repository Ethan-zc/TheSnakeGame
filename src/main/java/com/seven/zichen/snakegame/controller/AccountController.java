package com.seven.zichen.snakegame.controller;

import com.seven.zichen.snakegame.entry.RegisterEntry;
import com.seven.zichen.snakegame.service.AccountService;
import io.swagger.annotations.Api;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@Api(tags = "Account")
public class AccountController {

    @Autowired
    private AccountService accountService;

//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    public String register(@RequestBody RegisterEntry request) {
//        String accName = request.getAccname();
//        String pwd = DigestUtils.md5Hex(request.getPwd());
//        if (accName == null || accName.equals("") || pwd.equals("")) {
//            return "Account Name or password could not be empty. ";
//        } else {
////            if (accountService.findAccountByAccName(accName) != null) {
////                jsonObject.put("status", "fail");
////                jsonObject.put("message","accname");
////                return jsonObject;
////            }
//
//        }
//    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void testing() {
        System.out.println(accountService.getAllAccounts());
    }

}
