package rmi_archi;

import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

public class Neo4jWorker implements Runnable {
	private Driver driver;
	private Session session;
	private LinkedList<Person> persons;
	private Logger logger;
	
	public Neo4jWorker(LinkedList<Person> persons) {
		this.logger = Logger.getLogger(Neo4jWorker.class.getName());
		this.driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic("neo4j", "root"));
		this.session = driver.session();
		this.persons = persons;
	}
	
	public void run() {
		pushListToDb();
		session.close();
		driver.close();
		System.out.println("Neo4jWorker pushing complete");
	}
	
	private void pushListToDb() {
		try {
			for (Person each : persons) {				
				session.run("create (n) set n.name='" + each.getName() + "' "
						+ "set n.uid=" + each.getId());
				for(Entry<Long, String> entry : each.getRelationships().entrySet()){
					addRelationship(entry.getValue(), each.getId(), entry.getKey());
				}
			}
		} catch (Exception e){
			logger.log(Level.SEVERE, "Something wrong", e);
		}
	}
	
	public void addRelationship(String relation, long source, long target) {		
		try {
			session.run("MATCH (p { uid:" + source + "})" + 
				       "MATCH (m { uid:" + target + " })"+
				       "MERGE (p)-[r:" + relation + "]->(m)");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Something went wrong", e);
		}
	}
	
}