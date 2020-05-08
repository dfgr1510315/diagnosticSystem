package com.ljz.diagnostic_system;

import org.junit.jupiter.api.Test;

//@SpringBootTest
class DiagnosticSystemApplicationTests {

    public static class MyRunnable implements Runnable {
        private ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
        @Override
        public void run() {
            //一旦创建了一个ThreadLocal变量，你可以通过如下代码设置某个需要保存的值
            threadLocal.set((int) (Math.random() * 100D));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            //可以通过下面方法读取保存在ThreadLocal变量中的值
            System.out.println("-------threadLocal value-------"+threadLocal.get());
        }
    }

    @Test
    void TestLocal(){
        MyRunnable sharedRunnableInstance = new MyRunnable();
        Thread thread1 = new Thread(sharedRunnableInstance);
        Thread thread2 = new Thread(sharedRunnableInstance);
        thread1.start();
        thread2.start();
    }

    @Test
    void contextLoads() {

    }

}
