package rmi_getter;

import java.rmi.RemoteException;
import java.util.LinkedList;

import rmi_archi.Person;;

public class Manager implements ManagerMethods, WorkersListener {

	public void search(String name) throws RemoteException {
		
	}

	public void onReltionsSearchComplete(LinkedList<Person> relations) {		
	}

	public void onInfoSearchComplete(LinkedList<Person> responseList) {		
	}

}
