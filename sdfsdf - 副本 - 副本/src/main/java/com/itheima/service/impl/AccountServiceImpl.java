package com.itheima.service.impl;

import com.itheima.domain.Account;
import com.itheima.dao.IAccountDao;
import com.itheima.service.IAccountService;
import com.itheima.utils.TransactionManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/*
业务层账户的实现类

事务控制应该在业务层
 */
@Service("accountService")
public class AccountServiceImpl implements IAccountService {
    @Resource(name = "accountDao")
    private IAccountDao accountDao;

    @Resource(name = "transactionManager")
    private TransactionManager transactionManager;

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

    /*
       我们现在的这种写法是有问题的，如果
       如果在最后的一步updateAccount方法调用之前产生了异常，那么目标账户的金额就不会增加

       因此我们需要使用ThreadLocal对象把Connection和当前线程进行绑定，从而使一个线程中
       只有一个能控制事务的对象
       要达到的目的就是下面的这些数据库操作成为原子级操作，要执行就都执行，要么就全都不执行
    */
    public void transfer(String sourceName, String tragetName, float money) throws SQLException {
        //根据名称查询转出账户
        Account source = accountDao.findAccountByName(sourceName);
        //根据名称查询转入账户
        Account target = accountDao.findAccountByName(tragetName);
        //转出账户减钱
        source.setMoney(source.getMoney() - money);
        //转入账户加钱
        target.setMoney(target.getMoney() + money);
        //更新转出账户
        accountDao.updateAccount(source);

        int i = 1/0;
        //更新转入账户
        accountDao.updateAccount(target);
    }
}
