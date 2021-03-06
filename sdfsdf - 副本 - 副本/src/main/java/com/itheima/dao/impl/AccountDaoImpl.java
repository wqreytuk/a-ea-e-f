package com.itheima.dao.impl;

import com.itheima.dao.IAccountDao;
import com.itheima.domain.Account;
import com.itheima.utils.ConnectionUtils;
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
    @Resource(name = "runner")
    private QueryRunner runner;
    @Resource(name = "connectionUtils")
    private ConnectionUtils connectionUtils;

    public List<Account> findAllAccount() throws SQLException {
        return runner.query(connectionUtils.getThreadConnection(),"select * from account", new BeanListHandler<Account>(Account.class));
    }

    public Account findAccountById(Integer accountId) throws SQLException {
        return runner.query(connectionUtils.getThreadConnection(),"select * from account where id = ?", new BeanHandler<Account>(Account.class), accountId);
    }

    public void saveAccount(Account account) throws SQLException {
        runner.update(connectionUtils.getThreadConnection(),"insert into account (name, money) values(?, ?)", account.getName(), account.getMoney());
    }

    public void updateAccount(Account account) throws SQLException {
        runner.update(connectionUtils.getThreadConnection(),"update account set name=?, money=? where id=?", account.getName(), account.getMoney(), account.getId());
    }

    public void deleteAccount(Integer accountId) throws SQLException {
        runner.update(connectionUtils.getThreadConnection(),"delete from account where id=?",accountId);
    }

    public Account findAccountByName(String accountName) throws SQLException {
        try {
            List<Account> accounts = runner.query(connectionUtils.getThreadConnection(), "select * from account where name = ?", new BeanListHandler<Account>(Account.class), accountName);
            if(accounts == null || accounts.size() == 0) {
                return null;
            }
            if(accounts.size() > 1) {
                throw new RuntimeException("结果集不唯一");
            }
            return accounts.get(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
