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

import javax.annotation.Resource;
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
    @Resource(name = "accountService")
    private IAccountService as = null;

    @Test
    public void testTraansfer() throws SQLException {
        as.transfer("aaa","bbb", 100f);
    }
}
