package com.msi.threading;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class SumOfNumbersUsingCallable {

    public static int[] array = IntStream.rangeClosed(0,5000).toArray();
    public static int total = IntStream.rangeClosed(0,5000).sum();


    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Callable callable1 = () ->{
          int sum = 0;
          for (int i=0 ; i<array.length/2 ; i++){
              sum = sum + array[i];
          }
          return sum;
        };
        Callable callable2 = () ->{
          int sum = 0;
          for (int i=0 ; i<array.length/2 ; i++){
              sum = sum + array[i];
          }
          return sum;
        };

        ExecutorService service = Executors.newFixedThreadPool(2);
        List<Callable<Integer>> allTask = Arrays.asList(callable1, callable2);
        List<Future<Integer>> results = service.invokeAll(allTask);


        //Getting results

        int k = 0;
        int sum = 0;
        for(Future<Integer> result : results){
            sum = sum + result.get();
            System.out.println("Sum "+ ++k + "is: " + result.get() );
        }
        System.out.println("Sum from the threads is: " + sum);
        System.out.println("Correct sum from IntStream.sum is: "+ total);
        service.shutdown();
    }

}
