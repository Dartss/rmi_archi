package rmi_getter;

import java.util.LinkedList;

import rmi_archi.Person;

public class GetterBoss implements GetterBossMethods {
	
	public synchronized void addRequest(String reqestedName) {
		
	}
	
	public void registrateManager(ManagerMethods manager) throws RuntimeException {
		
	}

	public void onSearchComplete(LinkedList<Person> response) throws RuntimeException {
		
	}
	
	public static void main(String[] args) {
		Person p = new Person();
		
		System.out.println(p.getId());
	}

}
