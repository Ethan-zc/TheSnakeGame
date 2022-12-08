package com.seven.zichen.snakegame.service;

import com.seven.zichen.snakegame.entry.AccountEntry;
import com.seven.zichen.snakegame.entry.ScoreEntry;

import java.util.List;

public interface AccountService {

    List<AccountEntry> getAllAccounts();

    void createNewAccount(String accName, String pwd);

    AccountEntry getAccountByAccname(String accName);

    List<ScoreEntry> getScore();
}
