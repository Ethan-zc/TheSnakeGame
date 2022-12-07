package com.seven.zichen.snakegame.dao;

import com.seven.zichen.snakegame.entry.AccountEntry;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AccountMapper {

    @Select("SELECT * FROM acc")
    List<AccountEntry> getAllAccounts();

    @Insert("INSERT INTO acc (accname, pwd)" +
            "VALUES (#{accName}, #{pwd})")
    void addNewAccount(String accName, String pwd);

    @Select("SELECT * FROM acc WHERE accname = #{accName}")
    AccountEntry getAccountByAccname(String accName);

}
