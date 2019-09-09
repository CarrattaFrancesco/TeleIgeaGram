/**
 * 
 */

/**
 * @author coldwellbankergalatina
 *
 */
public class Message {

	private int update_id;
	private int message_id;
	private int chat_id;
	private String first_name;
	private String last_name;
	private Long date;
	private String text;
	
	
	Message(int update_id, int message_id, int chat_id, String first_name,String last_name, Long date, String text){
		this.update_id = update_id;
		this.message_id = message_id;
		this.chat_id = chat_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.date = date;
		this.text = text;
	}
	
	public void show() {
		System.out.println();
		System.out.println("	update_id: " + this.update_id);
		System.out.println("	message_id: " + this.message_id);
		System.out.println("	chat_id: " + this.chat_id);
		System.out.println("	from: " + this.first_name + " "+ this.last_name);
		System.out.println("	date: " + this.date);
		System.out.println("	text: " + this.text);
	}
	
	public int getMessage_id() {
		return this.message_id;
	}
	
	public int getChat_id() {
		return this.chat_id;
	}

	public String getText() {
		return this.text;
	}
	
	public Long getTime() {
		return this.date;
	}
}
