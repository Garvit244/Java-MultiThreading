import java.util.*;

class Worker {

	private Random random = new Random();

	private Object lock1 = new Object();
	private Object lock2 = new Object();

	private List<Integer> l1 = new ArrayList<Integer>();
	private List<Integer> l2 = new ArrayList<Integer>();

	public void inc1() {
		synchronized(lock1) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {

			}
			l1.add(random.nextInt(100));
		}
	}

	public void inc2() {
		synchronized(lock2) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {

			}
			l2.add(random.nextInt(100));
		}
	}

	public void process() {
		for(int i=0; i<1000; i++) {
			inc1();
			inc2();
		}
	}

	public void main() throws InterruptedException {
		long start = System.currentTimeMillis();
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				process();
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				process();
			}
		});


		t1.start();
		t2.start();

		t1.join();
		t2.join();
		long end = System.currentTimeMillis();
		System.out.println("Time Taken: " + (end-start));
		System.out.println("List1: " + l1.size() + " ; List2: " + l2.size());	
	}
}

public class MultiSynchronized {
	public static void main(String args[]) throws InterruptedException{
		new Worker().main();
	}
}