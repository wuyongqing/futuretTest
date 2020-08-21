import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class CompleteTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return "blog.csdn.net/zhangphil";
            }
        });

        System.out.println(System.currentTimeMillis() + ":time 1");

        future.whenCompleteAsync(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                System.out.println(System.currentTimeMillis() + ":" + s);
            }
        });

        System.out.println(System.currentTimeMillis() + ":time 2");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (Exception e) {
                    //异常退出。
                    future.completeExceptionally(e);
                }

                // CompletableFuture被通知线程任务完成。
                System.out.println(System.currentTimeMillis() + ":运行至此。");
                System.out.println(future.isDone());
                future.complete("任务完成。");
            }
        }).start();

        System.out.println(System.currentTimeMillis() + ":time 3");

        //System.out.println(future.get());
    }

}
