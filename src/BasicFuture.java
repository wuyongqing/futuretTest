import java.util.concurrent.*;

public class BasicFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int i = 1/0;
            return 100;
        });
        //future.join();
        //System.out.println(future.getNow(1));
        System.out.println(future.get());
    }

}
