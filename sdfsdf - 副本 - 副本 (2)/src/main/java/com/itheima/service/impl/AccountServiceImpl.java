package com.itheima.service.impl;

import com.itheima.domain.Account;
import com.itheima.dao.IAccountDao;
import com.itheima.service.IAccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/*
业务层账户的实现类
 */
@Service("accountService")
public class AccountServiceImpl implements IAccountService {

    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Resource(name = "accountDao")
    private IAccountDao accountDao;


    public List<Account> findAllAccount() throws SQLException {
        return accountDao.findAllAccount();
    }

    public Account findAccountById(Integer accountId) throws SQLException {
        return accountDao.findAccountById(accountId);
    }

    public void saveAccount(Account account) throws SQLException {
        accountDao.saveAccount(account);
    }

    public void updateAccount(Account account) throws SQLException {
        accountDao.updateAccount(account);
    }

    public void deleteAccount(Integer accountId) throws SQLException {
        accountDao.deleteAccount(accountId);
    }
}
