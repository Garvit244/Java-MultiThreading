import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Account {
	private int balance = 1000;

	public void deposit(int amount) {
		balance += amount;
	}

	public void withdraw(int amount) {
		balance -= amount;
	}

	public static void transfer(Account a1, Account a2, int amount) {
		a1.withdraw(amount);
		a2.deposit(amount);
	}

	public int getBalance(){
		return balance;
	}
}

class Processor {
	private Account a1 = new Account();
	private Account a2 = new Account();

	private Lock lock1 = new ReentrantLock();
	private Lock lock2 = new ReentrantLock();

	private void acquireLock(Lock l1, Lock l2) throws InterruptedException {
		while(true) {
			boolean flock = false;
			boolean slock = false;
			try {
				flock = l1.tryLock();
				slock = l2.tryLock();
			} finally {
				if(flock && slock) {
					return;
				}
				if(flock) {
					l1.unlock();
				}
				if(slock) {
					l2.unlock();
				}
			}
			Thread.sleep(1);
		}
	}

	public void f1() throws InterruptedException {
		Random random = new Random();
		for(int i=0; i < 1000; i++) {

			acquireLock(lock1, lock2);
			try {
				Account.transfer(a1, a2, random.nextInt(100));
			} finally {
				lock1.unlock();
				lock2.unlock();
			}
		}
	}

	public void f2() throws InterruptedException {
		Random random = new Random();
		for(int i=0; i < 1000; i++) {
			acquireLock(lock1, lock2);
			try {
				Account.transfer(a2, a1, random.nextInt(100));
			} finally {
				lock1.unlock();
				lock2.unlock();
			}
		}
	}

	public void finished() {
		System.out.println("Account1: " + a1.getBalance());
		System.out.println("Account2: " + a2.getBalance());
		System.out.println("Totl: " + (a1.getBalance() + a2.getBalance()));
	}
}

public class banking {
	public static void main(String args[]) throws InterruptedException {
		Processor processor = new Processor();
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try {
					processor.f1();
				} catch(InterruptedException e) {

				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try {
					processor.f2();
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