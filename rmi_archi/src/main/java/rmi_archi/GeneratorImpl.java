package rmi_archi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneratorImpl implements Generator {
	int personsNeeded;
	Logger logger = Logger.getLogger(Generator.class.getName());
	private static BossMethods server;
	
	public void generate(int personsAmount) {
		logger.log(Level.INFO, "Start generating");
		
		LinkedList<String> names = loadNames();
		LinkedList<Person> personsList = null;
		Person newPerson;
		//Generate random persons
		for (int j = 0; j < personsAmount; j += 10) {			
			personsList = new LinkedList<Person>();
			int randomAge = randomInt(80);
			boolean isStupid = false;
			
			if (randomAge % 2 == 0) {
				isStupid = true;
			}
			
			//Add random relationships for each person
			newPerson = new Person(generateName(names), randomAge, isStupid);
			newPerson.addRelationship("Friends", randomInt(10 + j));
			newPerson.addRelationship("Friends", randomInt(10 + j));
			newPerson.addRelationship("Friends", randomInt(10 + j));
			
			personsList.add(newPerson);
		}
			
		logger.log(Level.INFO, "Persons portion generating complete");
		
		try {
			server.onGenerationDone(personsList);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}		
	}
	
	private LinkedList<String> loadNames() {
		LinkedList<String> names = new LinkedList<String>();
		names.add("Zloi");
		names.add("Perdun");
		names.add("Tarakan");
		names.add("Zadok");
		names.add("Nemoi");
		names.add("Konan");
		names.add("Sobaka");
		names.add("Kran");
		names.add("Stoyak");
		names.add("Glaznica");
		names.add("Anusik");
		names.add("Vor");
		names.add("Oleg");
		names.add("Crazy");
		names.add("Neponiatniy");
		names.add("Salat");
		names.add("Blevok");
		names.add("Sosok");
		names.add("Love");
		names.add("Produkt");
		names.add("Ochko");
		names.add("Hava");
		names.add("Nagila");
		names.add("Potniak");
		return names;
	}
	
	private String generateName(LinkedList<String> names){
		String firstName = names.get(randomInt(names.size()));
		String lastName = names.get(randomInt(names.size()));
		return (firstName + " " + lastName);
	}
	
	private int randomInt(int range){
		int randomInt;
		Random rn = new Random();
		randomInt = rn.nextInt(range);
		return randomInt;
	}
	
	public static void main(String[] args) {
		GeneratorImpl generator = new GeneratorImpl();
		 
        try {
            Registry registry = LocateRegistry.getRegistry(null, 12345);
            server = (BossMethods)registry.lookup("GeneratorRegistrator");
            
            Generator stub = (Generator)UnicastRemoteObject.exportObject(generator, 0);
            server.registerGenerator(stub);
        } catch (Exception e) {
            System.out.println("Error occured: " + e.getMessage());
            System.exit (1);
        }
	}
}
