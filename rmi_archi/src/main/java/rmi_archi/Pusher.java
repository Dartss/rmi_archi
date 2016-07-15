package rmi_archi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

public interface Pusher extends Remote {
	public void push(LinkedList<Person> persons) throws RemoteException;
}
