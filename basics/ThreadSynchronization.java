
public class ThreadSynchronization {
	private int count = 0;

	private synchronized void oneUp() {
		count++;
	}

	public static void main(String args[]) throws InterruptedException {
		ThreadSynchronization app = new ThreadSynchronization();
		app.increment();
	}

	public void increment() throws InterruptedException {
		Thread t1 = new Thread(new Runnable(){
			public void run() {
				for(int i=0; i<100; i++) {
					oneUp();
				}
			}
		});


		Thread t2 = new Thread(new Runnable(){
			public void run() {
				for(int i=0; i<100; i++) {
					oneUp();
				}
			}
		});

		t1.start();
		t2.start();

		t1.join();
		t2.join();
		System.out.println("count is: " + count);
	}
}