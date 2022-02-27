package com.msi.threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PriorityBlockingQueue {
    public static void main(String[] args) {
        final String[] names = {"Masi", "Nari", "Sepehr", "Mike", "Henry", "Jenny"};
        final java.util.concurrent.PriorityBlockingQueue<String> queue = new java.util.concurrent.PriorityBlockingQueue<>();

        Runnable producer = () -> {
            for (String name : names) {
                queue.put(name);
            }
        };

        Runnable consumer = () -> {
            while (true) {
                try {
                    System.out.println(queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(producer);
        service.submit(consumer);
        service.shutdown();
    }
}
