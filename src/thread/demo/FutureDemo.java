package thread.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureDemo {
    public static void main(String[] args) {
        ServiceA serviceA = new ServiceA();
        ServiceB serviceB = new ServiceB();
        ServiceC serviceC = new ServiceC();

        long l = System.currentTimeMillis();
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Integer> resultA = executor.submit(() -> serviceA.calculate());

        CompletableFuture<Integer> integerCompletableFuture =
            CompletableFuture.supplyAsync(() -> serviceB.calculate())
            .thenCompose(aresult -> CompletableFuture.supplyAsync(() -> serviceC.calculate() + aresult));


        CompletableFuture<Integer> all = integerCompletableFuture.thenApply(total -> {
            try {
                return total + resultA.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return total;
        });

        try {
            System.out.println(all.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("Total spend " + (System.currentTimeMillis() - l));
    }

    static class ServiceA{
        public int calculate(){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 200;
        }
    }

    static class ServiceB{
        public int calculate(){
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 200;
        }
    }

    static class ServiceC{
        public int calculate(){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 200;
        }
    }
}
