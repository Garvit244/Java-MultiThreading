import java.util.concurrent.*;

class Processor implements Runnable {
	
	private int id;
	
	public Processor(int id) {
		this.id = id;
	}

	public void run() {
		System.out.println("Starting: " + id);

		System.out.println("Ending: " + id);
	}
}

public class ThreadPool {
	public static void main(String args[]) throws InterruptedException {
		ExecutorService pooling = Executors.newFixedThreadPool(2);

		for(int i=0; i<5; i++) {
			pooling.submit(new Processor(i));
		}

		pooling.shutdown();
		System.out.println("All submit");

		pooling.awaitTermination(1, TimeUnit.DAYS);
		System.out.println("All Done");
	}
}