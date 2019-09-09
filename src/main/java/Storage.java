import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

import com.google.gson.Gson;

//43200 unix --> 12 h
// TODO la gestione di veryold message noon mi piace, è da migliorare
/**
 * @author Francesco Carratta
 *Storage work as interface that translate data from local resource to object and elaborate them
 */
public class Storage {

	/**
	 * response is the list of response to the client
	 */
	private List<Response> responses;
	/**
	 * oldMessage is the list of message that have already reply in the last 24h
	 */
	private List<Message> oldMessages24h;
	/**
	 * 
	 */
	private List<Message> veryOldMessages;
	/**
	 * newMessage is the list of new message that should reply 
	 */
	private List<Message> newMessages;
	/**
	 * users in the database 
	 */
	private List<User> users;
	

	
	/**
	 * Costructor
	 */
	Storage(){
		this.newMessages = new ArrayList<>();
		this.inizialize("responses.json");
		this.inizialize("oldMessage24h.json");
		this.inizialize("veryOldMessage.json");
		this.inizialize("users.json");
	}
	
	/**
	 * function that inizialize parameter from json file
	 * @param filepath is the path of the file that want to inizialize ( resources.json or oldMessage24h.json)
	 * @param res is true if want to inizialize resource and false if want to inizialize oldmessagges
	 */
	private void inizialize(String filepath){
		
		List<Response> responseTmp = new ArrayList<>();
		List<Message> messageTmp = new ArrayList<>();
		List<User> userTmp = new ArrayList<>();
		
		//leggo il file
		File file = new File("src/main/resources/" + filepath); //TODO controllare con il jar se va bene
		 
		//Verifico che il file esista
		System.out.println("[info] - "+ filepath + " File Found : " + file.exists());
				
		if(file.exists()) {
		
			//se il file esiste trasformo il JSON in una lista di response usabili e stampo a video
			String content;
			try {
				//leggo il file e lo trasformo in string
				content = new String(Files.readAllBytes(file.toPath()));
				Gson gson = new Gson();
				
				Type listType;
				
				switch(filepath) {
					case "responses.json":
						listType = new TypeToken<List<Response>>(){}.getType();
						responseTmp = gson.fromJson(content, listType);
						//mostro a video le response
						for(Response resp :responseTmp) {
							resp.show();
						}
						System.out.println();
						this.responses = responseTmp;
				
						break;
					case "oldMessage24h.json":
						listType = new TypeToken<List<Message>>(){}.getType();
						messageTmp = gson.fromJson(content, listType);
						System.out.println("[info] - Inzialize old messages :");
						for(Message mes: messageTmp) {
							mes.show();
						}
						
						this.oldMessages24h = messageTmp;
						break;
					case "veryOldMessage.json":
						listType = new TypeToken<List<Message>>(){}.getType();
						messageTmp = gson.fromJson(content, listType);
						System.out.println("[info] - Inizialize messages older then 24 hour :");
						this.veryOldMessages = messageTmp;
						break;
					case "users.json":
						listType = new TypeToken<List<User>>(){}.getType();
						userTmp = gson.fromJson(content, listType);
						System.out.println("[info] - Inizialize users :");
						this.users = userTmp;
					default:
						System.out.println("[error] - Sorry but the name of the file to store isn't correct");
				}
					
				
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
			
		}else { // se il file non esiste, creo un array di response di default che andrà modificato
			
			if(filepath.equals("responses.json")) {
				String m1 = "/start";
				String r1 = "inial response, change it in resource/responses.json";
				Response res1 = new Response(m1,r1);
				responseTmp.add(res1);	
				
				this.responses = responseTmp;
			}
			
			//salvo il file
			this.store(filepath);
			
		}
	}
	
	/**
	 * Save file.json in the resources directory
	 *	@param type is the name of the json file
	 */
	private void store(String type) {
		Gson gson = new Gson();
		String objToString = null;
		boolean check = false;
		
		//traduco l'oggetto da salvare in base al filePath in una stringa JSON
		switch(type) {
			case "responses.json":
				objToString = gson.toJson(this.responses);
				check = true;
				break;
			case "oldMessage24h.json":
				objToString = gson.toJson(this.oldMessages24h);
				check = true;
				break;
			case "veryOldMessage.json":
				objToString = gson.toJson(this.veryOldMessages);
				check = true;
				break;
			case "users.json":
				objToString = gson.toJson(this.users);
				check = true;
				break;
				
			default:
				System.out.println("[error] - Sorry but the name of the file to store isn't correct");
		}
		
		//salvo il JSON nel file di riferimento
		if(check) {
			try {
				//leggo il file
				File file;
		
				file = new File("src/main/resources/" + type);
				
		
				FileWriter fileW = new FileWriter(file);
				fileW.write(objToString);
				System.out.println("[info] - Successfully Saved " + type );
				
				fileW.flush();
				fileW.close();
			}catch(IOException e) {
				e.printStackTrace();
			} 
		}

		
	}
	
	/**
	 * set responses function
	 * @param responses
	 */
	public void setResponses(List<Response> responses) {
		this.responses = responses;
	}

	
	/**
	 * compare oldMessages list to the list of input messages, if message not exist in oldMessage add to newMessage
	 * @param mex is a list of message
	 */
	public void filterNewMessages(List<Message> mex) {
		for(Message m:mex) {
			boolean find = false;
			
			for(Message oldMex: this.oldMessages24h) {
				if (oldMex.getMessage_id() == m.getMessage_id()) {
					find = true;
					break;
				}
			}
			
			if(!find) {
				this.newMessages.add(m);
			}
		}
		
	}
	
	/**
	 * get list of new messages
	 * @return lis of new messages
	 */
	public List<Message> getNewMessage(){
		return this.newMessages;
	}
	
	/**
	 * find the correct response to the message in input
	 * @param message
	 * @return
	 */
	public String findResponse(String message) {
		boolean find = false;
		String defaultResponse = " Scusa ma non esiste ancora una risposta";
		
		for(Response res : this.responses) {
			find = res.isSimilarMessage(message);
			if(find) return res.getResponse();
		}
		
		return defaultResponse;
	}

	/**
	 * Add newMessage to the oldMessage in local file
	 * @param newMessage is a list of Message
	 */
	public void storeInOldMessage(List<Message> newMessage) {
		this.oldMessages24h.addAll(newMessage);
		this.newMessages = new ArrayList<>();
		this.store("oldMessage24h.json");
	}
	
	
	/**
	 * this function store message older then 48h to a static file
	 */
	public void transferOldMessage(Long timeToTransfer) {
		List<Message> messageToTransfer = new ArrayList<>();
		long unixTime = System.currentTimeMillis() / 1000L - timeToTransfer;
		
		//cerco tutti i messaggi più vecchi di 48h
		for(Message mex:this.oldMessages24h) {
			if(mex.getTime() < unixTime) {
				messageToTransfer.add(mex);
			}
		}
		
		System.out.println("[info] - There are  " + messageToTransfer.size() + " old messages to transfer");
		
		//gli elimino da oldMessages
		this.oldMessages24h.removeAll(messageToTransfer);
		
		//salvo il nuovo oldmessages
		this.store("oldMessage24h.json");
		
		//salvo i messaggi più vecchi che non servono più
		this.veryOldMessages.addAll(messageToTransfer);
		this.store("veryOldMessage.json");
	
		 
	}

	
	/**
	 * this function search a user in user.json with that CF and if exist, add the chat_id
	 * @param m is the message form user
	 * @return true if find the user 
	 */
	public boolean registration(Message m) {
		
		for(User u:this.users) {
			if (u.isCorrectCF(m.getText())) {
				u.setChat_id(m.getChat_id());
				this.store("user.json");
				return true;
			}
		}
		
		return false;
	}
}
