package com.itheima.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Client {
    public static void main(String[] args) {
        //在下面的匿名内部类中需要访问producer对象，所以需要使用final关键字进行修饰
        final Producer producer = new Producer();

       /*
       动态代理
        特点：
            字节码随用随创建、随用随加载
        作用：
            不修改源码的基础上对方法进行增强
        分类：
            基于接口的动态代理
            基于子类的动态代理
        基于接口的动态代理
            涉及的类：Proxy
            提供者：JDK
        如何创建代理对象：
            使用Proxy类中的newProxyInstance方法
        创建代理对象的要求：
            被代理类最少实现一个接口，如果没有则不能使用
        newProxyInstance方法的参数：
            ClassLoader:
                类加载器
                用于加载代理对象的字节码
                固定写法：
                    被代理对象.getClass.ClassLoader()
            Class[]：
                字节码数组，用于让代理对象和被代理对象有相同的方法，固定写法：
                    被代理对象.getClass.getInterfaces()
                因为如果被代理对象和代理对象实现了同一个接口，那么他们就会拥有相同的方法
            InvocationHandler：
                用于提供增强的代码
                代理的具体代码，一般是一个接口的实现类，通常以匿名内部类的形式来实现
        */
        IProducer proxyProducer = (IProducer) Proxy.newProxyInstance(
                producer.getClass().getClassLoader(),
                producer.getClass().getInterfaces(),
                new InvocationHandler() {
            /*
            作用：
                执行被代理对象的任何接口方法都会经过该方法
                方法参数的含义
                    proxy:
                        代理对象的引用
                    method:
                        当前执行的方法
                    args:
                        当前执行方法所需要的参数
                返回值：
                    和被代理对象方法有相同的返回值
             */
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //提供增强的代码
                        Object returnValue = null;
                        //获取方法执行的参数
                        Float money = (Float)args[0];
                        //判断当前是否为sale方法
                        if("saleProduct".equals(method.getName())) {
                            returnValue = method.invoke(producer, money*0.8f);
                        }
                        return returnValue;
                    }
                }
        );
        proxyProducer.saleProduct(10000);
    }
}
