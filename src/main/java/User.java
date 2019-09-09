
/**
 * @author Francesco Carratta
 *
 */
public class User {

	private String first_name;
	
	private String last_name;
	
	private String cf;
	
	private int chat_id = 0;
	
	User(String first, String last, String cf){
		this.first_name = first;
		this.last_name = last;
		this.cf = cf;
	}
	
	public void setChat_id(int chat_id) {
		this.chat_id = chat_id;
	}
	
	public boolean isCorrectCF(String cf) {
		return this.cf.equals(cf);
	}
}
