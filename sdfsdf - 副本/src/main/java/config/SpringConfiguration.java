package config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/*
该类是一个配置类，我们创建它就是为了代替xml

spring中的新注解
@Configuration
    表明当前类是一个配置类
    当配置类作为AnnotationConfigApplicationContext的参数时，这个注解就不用写了

@ComponentScan
    用于通过注解指定spring在创建容器时要扫描的包
    属性
        value、basePackages
        这两个属性是互通的，对谁进行赋值都行，他们的值就是spring创建容器时将会扫描的包
    这个注解配置完成后，相当于xml中的这一行
        <context:component-scan base-package="com.itheima"></context:component-scan>
@Bean
    用于把当前方法中的返回值作为bean对象存入到spring的ioc容器中
    属性
        用于指定bean的id，如果不指定，默认值为当前方法的名称
    ***********************************************************************
    这里需要讲解一下为什么我们需要加上这@Bean注解
        如果我们不加，那么在我们这个方法中创建出来的对象是不会自动添加到
        spring的ioc容器中的
    加完这个注解之后，就和之前我们在xml中配置的这一行的作用是一样的了
        <bean id="runner" class="org.apache.commons.dbutils.QueryRunner" scope="prototype">
            <constructor-arg name="ds" ref="dataSource"/>
        </bean>
    ***********************************************************************
    当我们使用注解配置方法时，如果方法中有参数，那么spring会自动去ioc容器中查找
    有没有可用的bean对象，查找的方式和@AutoWired的方式是一样的

@Import
    用于导入其他的配置类
        可以实现主配置类加载分配置类的模式

@PropertySource
    用于指定properties文件的位置
    属性就是value
        classpath表示类路径
            classpath:jdbcConfig.properties
*/
@Configuration
@ComponentScan("com.itheima")
@Import(JdbcConfig.class)
@PropertySource("classpath:jdbcConfig.properties")
public class SpringConfiguration {
}
