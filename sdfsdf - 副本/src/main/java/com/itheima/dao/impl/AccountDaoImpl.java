package com.itheima.dao.impl;

import com.itheima.dao.IAccountDao;
import com.itheima.domain.Account;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/*
账户的持久层实现类
 */
@Repository("accountDao")
public class AccountDaoImpl implements IAccountDao {
    public void setRunner(QueryRunner runner) {
        this.runner = runner;
    }
    @Resource(name = "runner")
    private QueryRunner runner;

    public List<Account> findAllAccount() throws SQLException {
        return runner.query("select * from account", new BeanListHandler<Account>(Account.class));
    }

    public Account findAccountById(Integer accountId) throws SQLException {
        return runner.query("select * from account where id = ?", new BeanHandler<Account>(Account.class), accountId);
    }

    public void saveAccount(Account account) throws SQLException {
        runner.update("insert into account (name, money) values(?, ?)", account.getName(), account.getMoney());
    }

    public void updateAccount(Account account) throws SQLException {
        runner.update("update account set name=?, money=? where id=?", account.getName(), account.getMoney(), account.getId());
    }

    public void deleteAccount(Integer accountId) throws SQLException {
        runner.update("delete from account where id=?",accountId);
    }

}
