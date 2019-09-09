/**
 * 
 */


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Francesco Carratta
 * Telegram class is the interface that translate the response from the Telegram API to local object
 */
public class Telegram {

	//string di base per ogni richiesta 
	private String urlString = "https://api.telegram.org/bot" ;
	
	Telegram(String token){
		this.urlString = this.urlString.concat(token);
	}
	
	
	//funcione che crea la connessione con la chat del cliente e invia il messaggio 
	public int sendMessage(String chat_id, String text) {
		
		//dichiaro l'url da usare per l'invio dei messaggi di tipo string
		String urlStringTmp;
		urlStringTmp = this.urlString.concat("/sendMessage");
		
		try {
			URL url = new URL(urlStringTmp);
			//apro la connessione
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			//setto un timeout in caso di mancanza di connessione o lentezza
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			
			//creo un hashmap per elenco dei parametri
			Map<String,String> parameters = new HashMap<String, String>();
			parameters.put("chat_id", chat_id);
			parameters.put("text", text);
			 
			//inizializzo uno stream per l'invio dei parametri
			con.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
			out.flush();
			out.close();
			
			
			//leggo lo stato della risposta
			int status = con.getResponseCode();
			
			//chiudo la connessione	
			con.disconnect();
		
			return status;
		} catch (IOException e) {
			e.printStackTrace();
			//se c'è un problema interno ritorna 0
			return 0;
		}
		

	}
	
	
	public List<Message> getMessages(Long timeToTransfer) {
		//dichiaro l'url da usare per l'invio dei messaggi di tipo string
				String urlStringTmp;
				urlStringTmp = this.urlString.concat("/getUpdates");
				
				List<Message> messages = new ArrayList<>();
				
				try {
					URL url = new URL(urlStringTmp);
					//apro la connessione
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("GET");
					//setto un timeout in caso di mancanza di connessione o lentezza
					con.setConnectTimeout(5000);
					con.setReadTimeout(5000);
					
					//leggo lo stato della risposta
					con.getResponseCode();
					
					//leggo la risposta
					BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
					String inputLine;
					StringBuffer content = new StringBuffer();
					while ((inputLine = in.readLine()) != null) {
					    content.append(inputLine);
					}
					in.close();
					
					String response = content.toString();
					
					//trasformo la risposta in un JSON
					JSONParser parser = new JSONParser();
					JSONObject json = (JSONObject) parser.parse(response);
					
					JSONArray results = (JSONArray) json.get("result");
					
					//legge tutti i messaggi che sono stati inviati al bot
					for(Object o: results){
					    if ( o instanceof JSONObject ) {
					    	//filtro il singolo oggetto nei suoi sotto JSON 
					       JSONObject obj = (JSONObject) o;
					       JSONObject message = (JSONObject) obj.get("message");
					       JSONObject chat = (JSONObject) message.get("chat");
					       
					       //recupero i dati importanti
					       int update_id = ((Number) obj.get("update_id")).intValue();
					       int message_id = ((Number)message.get("message_id")).intValue();
					       int chat_id = ((Number) chat.get("id")).intValue();
					       String first_name = (String) chat.get("first_name");
					       String last_name = (String) chat.get("last_name");
					       Long date = (Long) message.get("date");
					       String text = (String) message.get("text");
					       
					       //creo l'oggetto messaggio
					       Message mex = new Message(update_id, message_id,chat_id, first_name,last_name, date,text);
					       //aggiungo il messaggio nell'array di messaggi solo se è meno vecchio dell'intervallo settato nel configuration.json
					       if((System.currentTimeMillis() / 1000L - timeToTransfer) < date) {
					    	   messages.add(mex);
					       }
					       
					       
					    }
					}
					
					
					//chiudo la connessione	
					con.disconnect();
	
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return messages;
	}

}