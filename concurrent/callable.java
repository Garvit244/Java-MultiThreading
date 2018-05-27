import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class callable {
	public static void main(String args[]) throws Exception {

		ExecutorService executor = Executors.newCachedThreadPool();
		
		Future<Integer> future = executor.submit(new Callable<Integer>() {
			public Integer call() throws Exception {
				Random random = new Random();
				int val = random.nextInt(100);
				System.out.println("Starting");
				try{
					Thread.sleep(val);	
				} catch(InterruptedException e) {

				}
				System.out.println("Finished");
				return val;
			}
		});

		executor.shutdown();
		System.out.println("Result is: " + future.get());
	}
}