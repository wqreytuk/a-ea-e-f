package com.itheima.dao;

import com.itheima.domain.Account;

import java.sql.SQLException;
import java.util.List;

/*
业务层的接口
 */
public interface IAccountDao {
    /*
    查询所有
     */
    List<Account> findAllAccount() throws SQLException;

    /*
    指定条件查询
     */
    Account findAccountById(Integer accountId) throws SQLException;

    /*
    保存
     */
    void  saveAccount(Account account) throws SQLException;

    /*
    更新
     */
    void updateAccount(Account account) throws SQLException;

    /*
    删除
     */
    void deleteAccount(Integer accountId) throws SQLException;
}
