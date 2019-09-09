import static org.junit.Assert.*;

import org.junit.Test;

public class ModelTest {


	@Test
	public void elaborateMessageTest() {
		String token = "972699262:AAGN-nNf7mS6c5m1JnRfwqn2Szg7-57qgr8";
		Model m = new Model();
		m.elaborateMessages();
	}
	
	@Test
	public void transferOldMessage() {
		String token = "972699262:AAGN-nNf7mS6c5m1JnRfwqn2Szg7-57qgr8";
		Model m = new Model();
		Storage s = m.getStorage();
		Long timeToTransfer = 172800L;
		
	}
	
}
