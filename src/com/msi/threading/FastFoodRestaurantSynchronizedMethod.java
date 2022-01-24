package com.msi.threading;

public class FastFoodRestaurantSynchronizedMethod {
    private String lastClientName;
    private int numberOfBurgerSold;

    public synchronized void buyBurger(String clientName) {
        alongRunningProcess();
        this.lastClientName = clientName;
        numberOfBurgerSold++;
        System.out.println(clientName + " bought a burger");
    }

    public void alongRunningProcess() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public String getLastClientName() {
        return lastClientName;
    }

    public int getNumberOfBurgerSold() {
        return numberOfBurgerSold;
    }

    public FastFoodRestaurantSynchronizedMethod setLastClientName(String lastClientName) {
        this.lastClientName = lastClientName;
        return this;
    }

    public FastFoodRestaurantSynchronizedMethod setNumberOfBurgerSold(int numberOfBurgerSold) {
        this.numberOfBurgerSold = numberOfBurgerSold;
        return this;
    }


    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        FastFoodRestaurantSynchronizedMethod fastFoodRestaurant = new FastFoodRestaurantSynchronizedMethod();

        Thread t1 = new Thread(() -> {
            fastFoodRestaurant.buyBurger("Mike");
        });

        Thread t2 = new Thread(() -> {
            fastFoodRestaurant.buyBurger("masi");
        });

        Thread t3 = new Thread(() -> {
            fastFoodRestaurant.buyBurger("Nari");
        });

        Thread t4 = new Thread(() -> {
            fastFoodRestaurant.buyBurger("Amy");
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        System.out.println("Total number of burgers sold: " + fastFoodRestaurant.getNumberOfBurgerSold());
        System.out.println("The last name of client is: " + fastFoodRestaurant.getLastClientName());
        System.out.println("Total execution time:" + (System.currentTimeMillis() - startTime) + " in msec");
    }
}
