import java.util.concurrent.*;

class Processor implements Runnable {

	private CountDownLatch latch;

	public Processor(CountDownLatch latch) {
		this.latch = latch;
	}

	public void run() {
		System.out.println("Starting");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {

		}

		latch.countDown();
	}
}

public class Latches {
	public static void main(String args[]) throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(3);
		ExecutorService pooling = Executors.newFixedThreadPool(3);

		for(int i=0; i<3; i++) {
			pooling.submit(new Processor(latch));
		}
		pooling.shutdown();

		latch.await();
		System.out.println("Completed");
	}
}