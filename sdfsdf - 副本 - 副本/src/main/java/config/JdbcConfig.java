package config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import config.SpringConfiguration;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

public class JdbcConfig {
    @Value("${jdbc.driver}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.user}")
    private String user;
    @Value("${jdbc.password}")
    private String password;

    /*
    如果我们仅仅使用@Bean注解，那么QueryRunner在容器中的对象将会只有一个
    也就是单例的，但是为了程序的效率，我们让它是多例的
    这时就需要@Scope注解
     */
    @Bean("runner")
    public QueryRunner createQueryRunner(DataSource dataSource) {
        return new QueryRunner();
    }

    /*
    数据库的配置不能直接硬编码到类文件中，这个地方是需要改进的
     */
    @Bean("dataSource")
    public DataSource createDataSource() {
        ComboPooledDataSource ds = new ComboPooledDataSource();
        try {
            ds.setDriverClass(driver);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        ds.setJdbcUrl(url);
        ds.setUser(user);
        ds.setPassword(password);
        return ds;
    }
}
