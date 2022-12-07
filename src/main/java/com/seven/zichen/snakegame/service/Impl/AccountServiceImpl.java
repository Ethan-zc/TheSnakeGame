package com.seven.zichen.snakegame.service.Impl;

import com.seven.zichen.snakegame.dao.AccountMapper;
import com.seven.zichen.snakegame.entry.AccountEntry;
import com.seven.zichen.snakegame.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    protected AccountMapper accountMapper;

    @Override
    public List<AccountEntry> getAllAccounts() {
        return accountMapper.getAllAccounts();
    }

    @Override
    public void createNewAccount(String accName, String pwd) {
        accountMapper.addNewAccount(accName, pwd);
    }

    @Override
    public AccountEntry getAccountByAccname(String accName) {
        return accountMapper.getAccountByAccname(accName);
    }
}
