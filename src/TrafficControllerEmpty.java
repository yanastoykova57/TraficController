
public class TrafficControllerEmpty implements TrafficController {
	private TrafficRegistrar registrar;
	boolean empty;
	

		public TrafficControllerEmpty(TrafficRegistrar r) {
			this.registrar = r;
			this.empty = true;
		}
		
		public synchronized void enterRight(Vehicle v) { 
			while(!empty) {
				try {
					wait();
				} catch(Exception e) {}
			}
			registrar.registerRight(v); 
			empty = false;
			notifyAll();
		}
		
		public synchronized void enterLeft(Vehicle v) {
			while(!empty) {
				try {
					wait();
				} catch(InterruptedException e) {}
			}
			registrar.registerLeft(v);   
			empty = false;
			notifyAll();
		}
		
		public synchronized void leaveLeft(Vehicle v) {  
			while(empty) {
				try {
					wait();
				} catch(InterruptedException e) {}
			}
			registrar.deregisterLeft(v);    
			empty = true;
			notifyAll();
		}
		
		public synchronized void leaveRight(Vehicle v) { 
			while(empty) {
				try {
					wait();
				} catch(InterruptedException e) {}
			}
			registrar.deregisterRight(v); 
			empty = true;
			notifyAll();
		}
	}
