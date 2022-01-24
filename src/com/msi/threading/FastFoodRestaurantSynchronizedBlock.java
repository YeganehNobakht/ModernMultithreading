package com.msi.threading;

public class FastFoodRestaurantSynchronizedBlock {
    private String lastClientName;
    private int numberOfBurgerSold;

    public void buyBurger(String clientName) {
        alongRunningProcess();
        System.out.println(clientName + " bought a burger");
        synchronized (this) {
            this.lastClientName = clientName;
            numberOfBurgerSold++;
        }
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

    public FastFoodRestaurantSynchronizedBlock setLastClientName(String lastClientName) {
        this.lastClientName = lastClientName;
        return this;
    }

    public FastFoodRestaurantSynchronizedBlock setNumberOfBurgerSold(int numberOfBurgerSold) {
        this.numberOfBurgerSold = numberOfBurgerSold;
        return this;
    }


    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        FastFoodRestaurantSynchronizedBlock fastFoodRestaurant = new FastFoodRestaurantSynchronizedBlock();

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
