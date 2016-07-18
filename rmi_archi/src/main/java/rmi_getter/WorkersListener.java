package rmi_getter;

import java.util.LinkedList;

import rmi_archi.Person;

public interface WorkersListener {
	public void onReltionsSearchComplete(LinkedList<Person> relations);
	
	public void onInfoSearchComplete(LinkedList<Person> responseList);
}
