import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class StorageTest {


	
	
	public void storeResponse() {
		Storage s = new Storage();
		List<Response> responses = new ArrayList<>();
		
		String m1 = "ciao";
		String r1 = "ciao a te";
		Response res1 = new Response(m1,r1);
				
		String m2 = "come stai?";
		String r2 = "bene bene te?";
		Response res2 = new Response(m2,r2);
		
		String m3 = "anch'io bene";
		String r3 = "mi fa piacere";
		Response res3 = new Response(m3,r3);
		
	}
	


}
