package com.itheima.test;

import com.itheima.domain.Account;
import com.itheima.service.IAccountService;
import config.SpringConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.List;

/*
使用junit进行单元测试
 */

/*
spring整合junit配置
    导入spring-test坐标
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>5.0.2.RELEASE</version>
            <scope>test</scope>
        </dependency>

    使用junit提供的@Runwith注解将junit原有的main方法替换成spring提供的main方法

    使用@ContextConfiguration注解告知spring的运行器，spring的ioc创建是基于xml还是基于注解的，并说明位置
        该注解有两个属性
            Locations：指定xml文件的位置，加上classpath关键字，表示在类路径下
            classes：指定注解配置类的位置

    当使用spring 5.x版本时，要求junit的版本必须是4.12及以上
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class AccountServiceTest {
    @Autowired
    private IAccountService as = null;

    @Test
    public void testFindAll() throws SQLException {
        List<Account> accounts = as.findAllAccount();
        for(Account account : accounts) {
            System.out.println(account);
        }
    }

    @Test
    public void testFindOne() throws SQLException {
        Account account = as.findAccountById(1);
        System.out.println(account);
    }

    @Test
    public void testSave() throws SQLException {
        Account account = new Account();
        account.setId(4);
        account.setMoney((float) 2000.0);
        account.setName("testaaa");
        as.saveAccount(account);
    }

    @Test
    public void testUpdate() throws SQLException {
        Account account = new Account();
        account.setId(3);
        account.setMoney((float) 2000.0);
        account.setName("testbbb");
        as.updateAccount(account);
    }

    @Test
    public void testDelete() throws SQLException {
        as.deleteAccount(5);
    }
}
