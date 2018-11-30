package thread.demo;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingDeque;

public class ThreadPoolDemo {
  private final Queue<Runnable> jobs = new LinkedBlockingDeque<>();

  public void execute(Runnable job) {
    jobs.offer(job);
    synchronized (jobs) {
      jobs.notify();
    }
  }

  public void start(int threadCount) {
    for (int i = 0; i < threadCount; i++) {
      Thread thread = create("TRD " + i);
      thread.start();
    }
  }

  public Thread create(String name) {
    return new Thread(() -> {
      while (true) {
        Runnable job = jobs.poll();
        if (job != null) {
          System.out.println("Running in " + Thread.currentThread());
          job.run();
        } else {
          synchronized (jobs) {
            try {
              jobs.wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
      }
    }, name);
  }


  public static void main(String[] args) {
    ThreadPoolDemo threadPoolDemo = new ThreadPoolDemo();
    threadPoolDemo.start(10);

    Scanner scan = new Scanner(System.in);
    while (true) {
      final String nextLine = scan.nextLine();
      if (!nextLine.isEmpty()) {
        threadPoolDemo.execute(() -> {
          try {
            Thread.sleep((long) (1000 + Math.random()*1000));
            System.out.println(nextLine);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        });
      }
    }
//    System.out.println("Finished");
  }
}
