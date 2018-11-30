package thread.demo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUseage {

  public static void main(String[] args) {

  }

  private void demo1(){
    ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
        new ArrayBlockingQueue<Runnable>(5));

    for (int i = 0; i < 15; i++) {
      final int index = i;
      executor.execute(() -> System.out.println("AAA" + index));
      System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
          executor.getQueue().size() + "，已执行玩别的任务数目：" + executor.getCompletedTaskCount());
    }
    executor.shutdown();
  }

  private void demo2(){
    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);

    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(20);
    scheduledExecutorService.schedule(() -> {}, 2000L, TimeUnit.MICROSECONDS);

    ExecutorService workStealingPool = Executors.newWorkStealingPool(5);
  }
}
