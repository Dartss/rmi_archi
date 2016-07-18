package rmi_getter;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import rmi_archi.Neo4jWorker;
import rmi_archi.Person;

public class NeoSearcher implements Runnable {
	private String requestName;
	private WorkersListener listener;
	private LinkedList<Person> relations;
	private Driver driver;
	private Session session;
	private Logger logger;
	
	public NeoSearcher(String requestName) {
		this.requestName = requestName;
		this.logger = Logger.getLogger(Neo4jWorker.class.getName());
		this.driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic("neo4j", "root"));
		this.session = driver.session();
		this.relations = new LinkedList<Person>();
	}

	public void run() {
		logger.log(Level.INFO, "Searching for relations started");
		searchForRelations();
		session.close();
		driver.close();
		logger.log(Level.INFO, "Searching for relations complete");
	}

	private void searchForRelations() {
		Person pers;
		try {
			StatementResult result = session.run( "MATCH (n {name:\"" + requestName + 
					"\"})-[:Friends]->(r) RETURN r");
			while ( result.hasNext() )
			{
			    Record record = result.next();
			    pers = new Person();
			    pers.setName(record.get("name").asString());
			    pers.setId(record.get("uid").asInt());
			    relations.add(pers);
			    System.out.println( record.get( "name" ).asString() + " " + record.get("uid").asInt() );
			}
		} catch (Exception e) {
			
		}
	}

}
