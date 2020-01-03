package com.itheima.proxy;

/*
用于规定对生产厂家要求的接口
 */
public interface IProducer {
    public abstract void saleProduct(float money);

    public abstract void afterService(float money);
}
