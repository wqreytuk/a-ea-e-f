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


//    public void setAccountDao(IAccountDao accountDao) {
//        this.accountDao = accountDao;
//    }

    public List<Account> findAllAccount() throws SQLException {
        try {
            //开启事务
            transactionManager.beginTransaction();
            //执行操作
            List<Account> accounts = accountDao.findAllAccount();
            //提交事务
            transactionManager.commit();
            //返回结果
            return accounts;
        } catch (Exception e) {
            //回滚操作
            transactionManager.rollback();
            throw new RuntimeException(e);
        } finally {
            //释放连接
            transactionManager.release();
        }
    }

    public Account findAccountById(Integer accountId) throws SQLException {
        try {
            //开启事务
            transactionManager.beginTransaction();
            //执行操作
            Account account = accountDao.findAccountById(accountId);
            //提交事务
            transactionManager.commit();
            //返回结果
            return account;
        } catch (Exception e) {
            //回滚操作
            transactionManager.rollback();
            throw new RuntimeException(e);
        } finally {
            //释放连接
            transactionManager.release();
        }
    }

    public void saveAccount(Account account) throws SQLException {
        try {
            //开启事务
            transactionManager.beginTransaction();
            //执行操作
            accountDao.saveAccount(account);
            //提交事务
            transactionManager.commit();
        } catch (Exception e) {
            //回滚操作
            transactionManager.rollback();
        } finally {
            //释放连接
            transactionManager.release();
        }
        accountDao.saveAccount(account);
    }

    public void updateAccount(Account account) throws SQLException {
        try {
            //开启事务
            transactionManager.beginTransaction();
            //执行操作
             accountDao.updateAccount(account);
            //提交事务
            transactionManager.commit();
        } catch (Exception e) {
            //回滚操作
            transactionManager.rollback();
        } finally {
            //释放连接
            transactionManager.release();
        }
    }

    public void deleteAccount(Integer accountId) throws SQLException {
        try {
            //开启事务
            transactionManager.beginTransaction();
            //执行操作
            accountDao.deleteAccount(accountId);
            //提交事务
            transactionManager.commit();
        } catch (Exception e) {
            //回滚操作
            transactionManager.rollback();
        } finally {
            //释放连接
            transactionManager.release();
        }
    }

    /*
       我们现在的这种写法是有问题的，如果
       如果在最后的一步updateAccount方法调用之前产生了异常，那么目标账户的金额就不会增加

       因此我们需要使用ThreadLocal对象把Connection和当前线程进行绑定，从而使一个线程中
       只有一个能控制事务的对象
       要达到的目的就是下面的这些数据库操作成为原子级操作，要执行就都执行，要么就全都不执行
    */
    public void transfer(String sourceName, String tragetName, float money) throws SQLException {
        try {
            //开启事务
            transactionManager.beginTransaction();
            //执行操作
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
            //提交事务
            transactionManager.commit();
        } catch (Exception e) {
            //回滚操作
            transactionManager.rollback();
            e.printStackTrace();
        } finally {
            //释放连接
            transactionManager.release();
        }
    }
}
