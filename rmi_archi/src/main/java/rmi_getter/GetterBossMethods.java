package rmi_getter;

import java.rmi.Remote;
import java.util.LinkedList;

import rmi_archi.Person;

public interface GetterBossMethods extends Remote {
	public void registrateManager(ManagerMethods manager) throws RuntimeException;
	
	public void onSearchComplete(LinkedList<Person> response) throws RuntimeException;
}
