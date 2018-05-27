import java.util.*;

class Processor {

	public void produce() throws InterruptedException {
		synchronized(this) {
			System.out.println("Producer running...");
			wait();
			System.out.println("Resumed");
		}
	}

	public void consume() throws InterruptedException {
		Scanner scanner = new Scanner(System.in);
		Thread.sleep(2000);

		synchronized(this) {
			System.out.println("Enter key");
			scanner.nextLine();
			notify();
			System.out.println("Key pressed");
			wait();
		}
	}
}

public class WaitNotify {

	public static void main(String args []) throws InterruptedException {

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