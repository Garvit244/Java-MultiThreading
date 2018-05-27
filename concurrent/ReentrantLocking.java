import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Processor{
	private int count = 0;
	private Lock lock = new ReentrantLock();


	private void increment() {
		for(int i=0; i<1000; i++) {
			count++;
		}
	}

	public void inc1() throws InterruptedException {
		lock.lock();
		try {
			increment();
		} finally {
			lock.unlock();
		}
	}

	public void inc2() throws InterruptedException {
		lock.lock();
		try {
			increment();
		} finally {
			lock.unlock();
		}
	}

	public void finished() {
		System.out.println("Count is: " + count);
	}
}

public class ReentrantLocking {
	public static void main(String args[]) throws InterruptedException {
		Processor processor = new Processor();
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try {
					processor.inc1();
				} catch(InterruptedException e) {

				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try {
					processor.inc2();
				} catch(InterruptedException e) {
					
				}
			}
		});

		t1.start();
		t2.start();

		t1.join();
		t2.join();

		processor.finished();
	}
}