package com.imooc.disruptor.height;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Disruptor中的Event
 */
@Setter
@Getter
public class Trade {

    private String id;

    private String name;

    private double price;

    private AtomicInteger count = new AtomicInteger(0);

    public Trade(){

    }
}
