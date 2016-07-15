package rmi_archi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Boss implements BossMethods {
	private Pusher manager;
	private Generator generator;
	private Logger logger = Logger.getLogger(Boss.class.getName());
	private boolean managerReady = false;
	
	public void isManagerReady(boolean isReady) throws RemoteException {
		this.managerReady = isReady;
	}
	
	public void onGenerationDone(LinkedList<Person> persons) throws RemoteException {
		logger.log(Level.INFO, "Trying to push");
		managerReady = false;
		manager.push(persons);
		logger.log(Level.INFO, "Boss said to push");
	}

	public void registerGenerator(Generator generator) throws RemoteException {
		this.generator = generator;
		logger.log(Level.INFO, "Generator registrated");
	}

	public void registerDBManager(Pusher manager) throws RemoteException {
		this.manager = manager;
		logger.log(Level.INFO, "Manager registrated");
	}
	
	public void startProcess(int generatorLimit) {
		logger.log(Level.INFO, "Starting boss process");
		while(generator == null || manager == null) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}						
		}
				
		while (true) {			
		    try {
		    	if(managerReady) {
		    		generator.generate(generatorLimit);
		    	}
			} catch (RemoteException e) {
				logger.log(Level.WARNING, "Client disconnected or unknown error occured", e);
			}
		}		
	}
	
	public static void main(String[] args) {
		Boss server = new Boss();
		 
        try {
            BossMethods stub = (BossMethods)UnicastRemoteObject.exportObject(server, 0);
               
            Registry registryDBM = LocateRegistry.createRegistry(12344);            
            registryDBM.bind("DBManagerRegistrator", stub);
            
            Registry registryGNR = LocateRegistry.createRegistry(12345);
            registryGNR.bind("GeneratorRegistrator", stub);
            
            server.startProcess(10);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit (1);
        }
	}

}
