import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class BasicMain {
    public static CompletableFuture<Integer> compute() {
        final CompletableFuture<Integer> future = new CompletableFuture<>();
        return future;
    }

    public static void main(String[] args) throws Exception {
        final CompletableFuture<Integer> f = compute();
        //f.get();
        class Client extends Thread {
            CompletableFuture<Integer> f;
            Client(String threadName, CompletableFuture<Integer> f) {
                super(threadName);
                this.f = f;
            }

            @Override
            public void run() {
                try {
                    if (this.getName().equals("Client3")) {
                        f.completeExceptionally(new Exception());
                    }
                    System.out.println(this.getName() + ": " + f.get() + "--" + new Timestamp(System.currentTimeMillis()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        new Client("Client1", f).start();
        new Client("Client2", f).start();
        new Client("Client3", f).start();
        new Client("Client4", f).start();
        new Client("Client5", f).start();
        new Client("Client6", f).start();
        System.out.println("waiting1");
        f.complete(100);
        System.in.read();
        System.out.println("waiting2");
    }
}
