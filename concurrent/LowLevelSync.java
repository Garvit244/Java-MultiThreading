import java.util.*;

class Processor {

	private LinkedList<Integer> list = new LinkedList<Integer>();
	private final int LIMIT = 10;
	private Object lock = new Object();

	public void produce() throws InterruptedException {
		int value = 0;
		while(true) {
			synchronized(lock) {
				while(list.size() == LIMIT) {
					lock.wait();
				}

				list.add(value++);	
				lock.notify();
			}
		}
	}

	public void consume() throws InterruptedException {
		while(true) {
			synchronized(lock) {
				while(list.size() == 0) {
					lock.wait();
				}
				System.out.print("List Size: " + list.size());
				int value = list.removeFirst();
				System.out.println(" Removed Element: " + value);
				lock.notify();
			}
			Thread.sleep(100);
		}
	}
}


public class LowLevelSync {
	public static void main(String args[]) throws InterruptedException {
		Processor processor = new Processor();
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try {
					processor.produce();
				} catch(InterruptedException e) {

				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try {
					processor.consume();
				} catch(InterruptedException e) {
					
				}
			}
		});

		t1.start();
		t2.start();

		t1.join();
		t2.join();
	}
}