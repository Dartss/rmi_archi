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
			newPerson = new Person(generateName(), randomAge, isStupid);
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
	
	private LinkedList<String> loadFirstNames() {
		LinkedList<String> names = new LinkedList<String>();
		names.add("Zloi");
		names.add("Golodniy");
		names.add("Skuchniy");
		names.add("Triapka");
		names.add("Nemoi");
		names.add("Kisliy");
		names.add("Sobaka");
		names.add("Brehun");
		names.add("Neznaika");
		names.add("Glavniy");
		names.add("Bolshoi");
		names.add("Sladkiy");
		names.add("Mazohist");
		names.add("Crazy");
		names.add("Neopitniy");
		names.add("Narkoman");
		names.add("Prostitutka");
		names.add("Sosok");
		names.add("Poriadochniy");
		names.add("Otstaliy");
		names.add("Slepoiy");
		names.add("Zanuda");
		names.add("Goluboiy");
		names.add("Bedniy");
		return names;
	}
	
	private LinkedList<String> loadLastNames() {
		LinkedList<String> names = new LinkedList<String>();
		names.add("Evreiy");
		names.add("Perdun");
		names.add("Tarakan");
		names.add("Zadok");
		names.add("Tolik");
		names.add("Konan");
		names.add("Perdak");
		names.add("Kran");
		names.add("Stoyak");
		names.add("Tupen");
		names.add("Anusik");
		names.add("Vor");
		names.add("Android");
		names.add("TeamLead");
		names.add("Kolokol");
		names.add("Salat");
		names.add("Blevok");
		names.add("Sosok");
		names.add("Shahid");
		names.add("Produkt");
		names.add("Ochko");
		names.add("Krikun");
		names.add("Vitalik");
		names.add("Potniak");
		return names;
	}
	
	private String generateName(){
		LinkedList<String> first = loadFirstNames();
		LinkedList<String> last = loadLastNames();
		int amount = first.size();
		String firstName = first.get(randomInt(amount));
		String lastName = last.get(randomInt(amount));
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
