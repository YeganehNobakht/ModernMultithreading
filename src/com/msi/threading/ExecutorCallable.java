package com.msi.threading;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorCallable {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Callable<String> callable = ()->{
            TimeUnit.MILLISECONDS.sleep(1000);
            return "Current Time is: " + LocalDateTime.now();
        };

        ExecutorService service = Executors.newFixedThreadPool(1);

        List<Callable<String>> taskList = Arrays.asList(callable, callable,callable);
        List<Future<String>> results = service.invokeAll(taskList);

        for (Future<String> result : results){
            System.out.println(result.get());
        }

        System.out.println("Executing callable using submit");
        Future<String> result = service.submit(callable);

        while (!result.isDone()){
            System.out.println("The method return value: " + result.get());
        }

        service.shutdown();
    }
}
