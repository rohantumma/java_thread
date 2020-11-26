/**
 * 
 */
package java_thread;

import java.util.concurrent.TimeUnit;

/**
 * @author rohan
 *
 */

//inter thread communication

class InterTh{
	int num;
	boolean vset =false;
	
    synchronized void put(int num) {
		// TODO Auto-generated method stub
    	while (vset){
    		try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	this.num= num;
    	
    	System.out.println("::"+num);
    	
    	vset= true;
    	notify();
	}
    synchronized void get() {
		// TODO Auto-generated method stub
    	while (!vset){
    		try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	System.out.println(":::"+num);
    	vset= false;
    	notify();
	} 
}

class Producer implements Runnable{
	InterTh q;
	
	public Producer(InterTh q) {
		// TODO Auto-generated constructor stub
		this.q =q;
		Thread t = new Thread(this,"Producer");
		t.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		int i=0;
		while( true)
		{
			q.put(i++);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}


class consumer implements Runnable{
	
	InterTh q;
	
	public consumer(InterTh q) {
		// TODO Auto-generated constructor stub
		this.q =q;
		Thread t = new Thread(this,"Consumer");
		t.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		while( true)
		{
			q.get();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}






//synchronize
class counter{
	 int counter ;
	
     synchronized void incr() {
		// TODO Auto-generated method stub
		counter++;
	}
}


public class Thread_demo {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("Pandas 1");
		
		//new ThreadDemo();
		//new ThreadDemo();
		
		Thread t1 = new Thread( () -> 
		{
			for (int i=0; i<5; i++)
			{
				System.out.println("Hi");
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
         });
		
		Thread t2 = new Thread(() ->
		{
			for (int i=0; i<5; i++)
			{
				System.out.println("Hello");
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		// praoity 1 to 10
		t1.setPriority(9);
		t1.setPriority(1);
		
		System.out.println(t1.getName());
		System.out.println(t2.getName());
		
		t1.setName("Hi thread");
		t2.setName("Hellow Thread");
		
		System.out.println(t1.getName());
		System.out.println(t2.getName());
		
		
		
		
		
		
		t1.start();
		t2.start();
		
		t1.setName("Hi thread");
		t2.setName("Hellow Thread");
		
		System.out.println(t1.getName());
		System.out.println(t2.getName());
		
		
		System.out.println(t1.isAlive());
		
		t1.join();
		t2.join();

		System.out.println("Pandas 2");
		
		
		
		
		
		counter c = new counter();
		
		Thread tt1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				for  ( int i =0; i<10;i++)
				{
					c.incr();
				}
			}
		});  
		
		Thread tt2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				for  ( int i =0; i<10;i++)
				{
					c.incr();
				}
			}
		});  
		
		tt1.start();

		tt2.start();
		
		tt1.join();
		tt2.join();
		
		
		System.out.println("count :: "+ c.counter);
		
		
		
		InterTh q =new InterTh();
		new Producer(q);
		new consumer(q);
	}

}





class ThreadDemo extends Thread{
	
	private static int count = 0;
	private int id; 
	
	@Override
	public void run(){
		for (int i = 10;i>0 ; i--){
			System.out.println("Thread "+id+":: counter: "+i);
			
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public ThreadDemo(){
		
		this.id = ++count;
		this.start();
		
	}
}