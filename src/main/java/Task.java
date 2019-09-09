import java.util.TimerTask;

/**
 * 
 */

/**
 * @author Francesco Carratta
 *
 */
public class Task extends TimerTask {
    
	private Model model;
	private int type;
	
	Task(Model m, int type){
		this.model = m;
		this.type = type;
		
	}
	
	
	public void run() {
        switch(type) {
	        case 1:
	        	this.model.elaborateMessages();
	        	break;
	        case 2:
	        	this.model.transferOldMessage();
	        	break;
        	default:
        		System.out.println("[error] - Sorry but the type of the task isn't correct");
        }
    }
}
