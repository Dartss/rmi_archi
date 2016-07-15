package rmi_archi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBManager implements Pusher {
	private static BossMethods server;
	Logger logger = Logger.getLogger(DBManager.class.getName());

	public void push(LinkedList<Person> persons) throws RemoteException {
		logger.log(Level.INFO, "Pushing starts");
		System.out.println((persons.isEmpty())? "Persons list is empty " : persons.toString());
		Thread neoWorker = new Thread(new Neo4jWorker(persons));
		Thread elasticWorker = new Thread(new ElasticWorker(persons));
		neoWorker.start();
		elasticWorker.start();
		
		try {
			elasticWorker.join();			
			neoWorker.join();
		} catch(Exception e) {
			logger.log(Level.ALL, "OOOpppps", e);
		}
		
		server.isManagerReady(true);
	}
		
	public static void main(String[] args) {
		DBManager manager = new DBManager();
		 
        try {
            Registry registry = LocateRegistry.getRegistry(null, 12344);
            server = (BossMethods)registry.lookup("DBManagerRegistrator");
 
            Pusher stub = (Pusher)UnicastRemoteObject.exportObject(manager, 0);            
            server.isManagerReady(true);
            server.registerDBManager(stub);
        } catch (Exception e) {
            System.out.println("Error occured: " + e.getMessage());
            System.exit (1);
        }
	}
}
