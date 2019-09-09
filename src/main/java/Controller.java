import java.util.Date;
import java.util.Timer;

/**
 * 
 */

/**
 * @author coldwellbankergalatina
 *
 */
public class Controller {

	private Model model;
	
	
	
	Controller(){
		this.model = new Model();
	}
	
	public void start() {
		Timer t1 = new Timer();
		Timer t2 = new Timer();
		
		Task task1 = new Task(this.model,1);
		Task task2 = new Task(this.model,2);
		
		t1.scheduleAtFixedRate(task1,new Date(),2000);
		t2.scheduleAtFixedRate(task2,new Date(),10000);
	}
	
}
