import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class TrafficControllerFair implements TrafficController {
	private TrafficRegistrar registrar;
	private boolean empty;
	Lock l = new ReentrantLock(true);
	Condition placeEmpty = l.newCondition();
	
	public TrafficControllerFair(TrafficRegistrar r) {
		this.registrar = r;
		this.empty = true;
	}

	public void enteringRL(Vehicle v, String direction){
		l.lock();
		try {
			
			while(!empty) {placeEmpty.await();}
			empty = false;
			placeEmpty.signalAll();

			if(direction == "R"){
			registrar.registerRight(v); 
			} else if (direction == "L")  {
			registrar.registerLeft(v);
			} 

		} catch (InterruptedException e) {}
		
		finally {l.unlock();}

	}
	public void enterRight(Vehicle v){ 
		/*l.lock();
		
		try {
			
			while(empty) {placeEmpty.await();}
			registrar.registerRight(v);   
			empty = false;
			placeEmpty.signalAll();

		} catch (InterruptedException e) {}
		
		finally {l.unlock();}*/
		enteringRL(v, "R");
	}
	
	public void enterLeft(Vehicle v) {
		/*l.lock();
		
		try {
			
			while(empty) {placeEmpty.await();}
			registrar.registerLeft(v); 
			empty = false;
			placeEmpty.signalAll();

		} catch (InterruptedException e) {}
		
		finally {l.unlock();} */
		enteringRL(v, "L"); 
	}
	
	public void leaveLeft(Vehicle v) { 
		l.lock();
		
		try {
			
			while(empty) {placeEmpty.await();}
			registrar.deregisterLeft(v);  
			empty = true;
			placeEmpty.signalAll();

		} catch (InterruptedException e) {}
		
		finally {l.unlock();}        
	}

	public void leaveRight(Vehicle v) { 
		l.lock();
		
		try {
			
			while(empty) {placeEmpty.await();}
			registrar.deregisterRight(v);   
			empty = true;
			placeEmpty.signalAll();

		} catch (InterruptedException e) {}
		
		finally {l.unlock();}  
	}
}