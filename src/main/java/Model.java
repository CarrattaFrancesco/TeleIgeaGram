import java.util.List;

/**
 * @author Francesco Carratta
 * Model is the main class where there is all function that work with the other objects
 */
public class Model {
	
	/**
	 * tel is the object link to telegram bot
	 */
	private Telegram tel;
	
	/**
	 * sto is the object link to the resource files
	 */
	private Storage sto;
	
	static final private long timeToTransfer = 86400L; //TODO salvare in un file configuration.json
	

	Model(){
		String token = "972699262:AAGN-nNf7mS6c5m1JnRfwqn2Szg7-57qgr8";
		this.tel = new Telegram(token);
		this.sto = new Storage();
	}
	
	/**
	 * this function ask for new messages from Telegram, reply and store them
	 */
	public void elaborateMessages() {
		//chiedo a telegram la lista di tutti gli ultimi messaggi
		List<Message> mex = this.tel.getMessages(timeToTransfer);
		
		
		//effettuo un filtraggio comparandoli con i vecchi messaggi
		System.out.println();
		System.out.println("[info] - Ask for new messages ...");
		this.sto.filterNewMessages(mex);
		
		//rispondo ai nuovi messaggi
		List<Message> messageToReply = this.sto.getNewMessage();
		
		System.out.println("[info] - There are " + messageToReply.size() + " new messages");
		
		if(!messageToReply.isEmpty()) {
			for(Message m:messageToReply) {
				String response = this.sto.findResponse(m.getText());
				//controllo sul fatto che è il messaggio ininiziale, quindi si avvia la registrazione
				if(m.equals("/start")) {
					this.tel.sendMessage(String.valueOf(m.getChat_id()), this.sto.registration(m));
				}else {
					this.tel.sendMessage(String.valueOf(m.getChat_id()), response);
				}
				
			}
			
			
			//svuoto i nuovi messaggi e li aggiungo ai vecchi
			this.sto.storeInOldMessage(messageToReply);
		}
		

	}
	
	
	public void transferOldMessage() {
		this.sto.transferOldMessage(timeToTransfer);
	}
	
	public Storage getStorage() {
		return this.sto;
	}
	
	//TODO ogni 24h c'è una funzione che elimina alcuni dei vecchi mesaggi e li mette in un json a parte
	
	
	
	
}
