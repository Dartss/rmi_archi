package rmi_archi;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;


public class ElasticWorker implements Runnable {
	public static final String URL = "http://localhost:9200/teamate/person";
	public static final MediaType JSON = MediaType.parse("person; charset=utf-8");
	private Logger logger;
	private LinkedList<Person> persons;
	private OkHttpClient client;
	
	public ElasticWorker(LinkedList<Person> persons) {
		this.logger = Logger.getLogger(ElasticWorker.class.getName());
		this.persons = persons;
		this.client = new OkHttpClient();
	}

	public void run() {
		System.out.println("ElasticWorker pushing Starts");
		addNewPersonsList();
		System.out.println("ElasticWorker pushing complete");
	}
	
	public void addNewPersonsList() {
		for (Person newPerson : persons) {
			String json = "{ \"isStupid\": " + newPerson.isStupid() + ", \"name\": \"" + newPerson.getName() 
				+ "\", " + " \"uid\":" + newPerson.getId() + "}";
						
			RequestBody body = RequestBody.create(JSON, json);
			
			try {
				Request request = new Request.Builder().url(URL+"/" + newPerson.getId()).put(body).build();
				client.newCall(request).execute();
			} catch (Exception e) {
				logger.log(Level.ALL, "Request Faile ", e);
			}
		}
	}
}
