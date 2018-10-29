package com.imooc.disruptor.mulit;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Disruptor中的Event
 */
@Setter
@Getter
public class Order {

    private String id;

    private String name;

    private double price;


}
