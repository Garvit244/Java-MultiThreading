
class Runner extends Thread {
	public void run() {
		for(int i=0; i<10; i++) {
			System.out.println("Hello " + i);

			try {
				Thread.sleep(100);
			} catch(InterruptedException ex) {

			}
		}
	}
}

class Runnerable implements Runnable {
	public void run() {
		for(int i=0; i<10; i++) {
			System.out.println("Hello " + i);

			try {
				Thread.sleep(100);
			} catch(InterruptedException ex) {

			}
		}
	}
}
public class SimpleThread {
	public static void main(String args[]){
		// Runner runner1 = new Runner();
		// Runner runner2 = new Runner();

		Thread runner1 = new Thread(new Runnerable());
		Thread runner2 = new Thread(new Runnerable());
		runner2.start();
		runner1.start();
	}
}