package com.imooc.disruptor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class Review {
    public static void main(String[] args) throws Exception {

//        ConcurrentHashMap<String,String> map = new ConcurrentHashMap<>();
//
//        CopyOnWriteArrayList<String> cowal = new CopyOnWriteArrayList<>();
//
//        cowal.add("eee");
//
//        AtomicLong atomicLong = new AtomicLong(1);
//        boolean flag = atomicLong.compareAndSet(1,2);
//        System.err.println(flag);
//        System.err.println(atomicLong.get());

        //用锁阻塞，用线程唤醒
//
//        Object lock = new Object();
//        Thread A = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int sum = 0;
//                for (int i = 0; i< 10; i++){
//                    sum += i;
//                }
//                synchronized (lock){
//                    try {
//                        lock.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                //park前后顺序调换
//                try {
//                    Thread.sleep(4000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                LockSupport.park(); //后执行
//                System.err.println("sum: "+sum);
//            }
//        });
//
//        A.start();
//
//        Thread.sleep(1000);

//        synchronized (lock){
//            lock.notify();
//        }
//        LockSupport.unpark(A); //先执行

        /*

        Executors.newCachedThreadPool();
        Executors.newFixedThreadPool(10);


        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                5,
                Runtime.getRuntime().availableProcessors() * 2,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(200),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        t.setName("order--thread");
                        if (t.isDaemon()) {
                            t.setDaemon(false);
                        }
                        if (Thread.NORM_PRIORITY == t.getPriority()) {
                            t.setPriority(Thread.NORM_PRIORITY);
                        }
                        return t;
                    }
                },
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.err.println("拒接策略： "+ r);
                    }
                }
        );

*/

        ReentrantLock reentrantLock = new ReentrantLock(true);

    }

}

