package thread.demo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ThreadSafe {
  private AtomicInteger count = new AtomicInteger(0);
  private AtomicReference<Map> map =new AtomicReference<>();

  public void increaseTimes(int time) {
    for (int i = 0; i < time; i++) {
      count.incrementAndGet();
    }
  }

  public int getCount() {
    return count.get();
  }

  public static void main(String[] args) {
    ThreadSafe threadSafe = new ThreadSafe();

    List<Thread> threads = IntStream.range(0, 10).mapToObj(i -> createThread(threadSafe, 2000)).collect(Collectors.toList());

    threads.forEach(Thread::start);
    for (Thread thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    //should be 20000
    System.out.println(threadSafe.getCount());
  }

  private static Thread createThread(ThreadSafe threadSafe, int increateTime) {
    return new Thread(() -> {
      threadSafe.increaseTimes(increateTime);
    });
  }
}
