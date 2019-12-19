package com.itheima.utils;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/*
连接的工具类，用于从数据源中获取一个连接，并且实现和线程的绑定
 */
@Component("connectionUtils")
public class ConnectionUtils {
    private ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
    @Resource(name = "dataSource")
    private DataSource dataSource;
    /*
    获取当前线程上的连接
     */
    public Connection getThreadConnection() {
        //先从ThreadLocal上获取
        Connection conn = tl.get();
        //判断当前线程上是否有连接
        if(conn == null) {
            //从数据源中获取一个连接，并且存入ThreadLocal中
            try {
                conn = dataSource.getConnection();
                tl.set(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    public void removeConnection() {
        //对线程和连接进行解绑
        tl.remove();
    }
}
